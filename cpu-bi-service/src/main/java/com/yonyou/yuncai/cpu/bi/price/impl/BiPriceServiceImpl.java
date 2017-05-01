package com.yonyou.yuncai.cpu.bi.price.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.cpu.commons.domain.response.ServiceResponse;
import com.yonyou.cpu.material.service.IMaterialService;
import com.yonyou.yuncai.cpu.basedoc.api.supplydocument.ISupplyDocService;
import com.yonyou.yuncai.cpu.bi.dao.price.BiPriceDao;
import com.yonyou.yuncai.cpu.bi.domain.price.BiPriceConstant;
import com.yonyou.yuncai.cpu.bi.domain.price.BiPriceEntity;
import com.yonyou.yuncai.cpu.bi.service.price.IBiPriceService;
import com.yonyou.yuncai.cpu.bi.utils.database.DataAccessUtils;
import com.yonyou.yuncai.cpu.bi.utils.database.IRowSet;
import com.yonyou.yuncai.cpu.bi.utils.database.TempTableTool;
import com.yonyou.yuncai.cpu.bi.utils.pub.JavaType;
import com.yonyou.yuncai.cpu.domain.dto.supplydocument.SupplyDocPOJO;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fengjqc on 2017/3/22.
 */
@Service("iBiPriceService")
public class BiPriceServiceImpl implements IBiPriceService {
    private static final Logger logger = LoggerFactory.getLogger(BiPriceServiceImpl.class);
    @Autowired
    BiPriceDao biPriceDao;

    @Resource
    IMaterialService materialService;

    @Autowired
    ISupplyDocService iSupplyDocService;

    @Autowired
    DataSource dataSource;


    @Transactional
    public void tempTableTest(BiPriceEntity biPriceEntity) {
            List<List<Object>> datas = new ArrayList<List<Object>>();
            for (int i = 0; i < 2; i++) {
                List<Object> periodRow = new ArrayList<Object>();
                periodRow.add("vpurchaseEnterpriseId"+i);// name
                periodRow.add("vmaterialId"+i);// beginDate
                periodRow.add("vsupplyEnterpriseId"+i);// endDate
                datas.add(periodRow);
            }
//            tempTableTool.createTempTable();
            TempTableTool tempTableTool = new TempTableTool(this.dataSource);
            tempTableTool.getTempTable("Temp_cpu_bi_hisprice", new String[] {
                     "vpurchaseEnterpriseId", "vmaterialId", "vsupplyEnterpriseId"
            }, new String[] {
                   "varchar(200)", "varchar(200)", "varchar(200)"
            }, new JavaType[] {
                    JavaType.String, JavaType.String, JavaType.String
            }, datas);
            DataAccessUtils dataAccessUtils = new DataAccessUtils(this.dataSource);
            IRowSet rs =  dataAccessUtils.query(" select * from Temp_cpu_bi_hisprice ");
            String name = null;

//        this.insert(biPriceEntity);
//        List<BiPriceEntity> list = new ArrayList<BiPriceEntity>();
//        list.add(biPriceEntity);
//        List<BiPriceEntity> list1 =this.selectBatchByList(list);
//        String name = null;
//        int n = 1/0;
    }

    public int updateByDefSql(String updateSql) {
        return biPriceDao.updateByDefSql(updateSql);
    }

    public int insert(BiPriceEntity biPriceEntity) {
        return biPriceDao.insert(biPriceEntity);
    }

    /**
     *
     * @param biPriceEntityList
     * @return
     */
    public int insertBatch(List<BiPriceEntity> biPriceEntityList) {
        logger.info("insertBatch："+biPriceEntityList);
        int num=0;
        if(biPriceEntityList!=null&&biPriceEntityList.size()>0) {
            List<BiPriceEntity> insertList =this.processMultiData(biPriceEntityList);
            if(insertList.size()>0){
                //1、设置更新标志
                final String updateMark = this.processUpdateMark(insertList);
                logger.info("本次同步Erp历史价格数据的更新标识为："+updateMark);
                //2、根据物料的管理id，更新物料的云采库id
                this.processMaterialYcId(insertList);
                //3、根据供应商的id，更新物料
                this.processSupplyEnterpriseId(insertList);
                //4、插入数据
                num = biPriceDao.insertBatch(insertList);
                //5、更新字符串到json数据
                //TODO
                //6、根据单据id和更新标识，删除非本次的数据
                //TODO
            }
            logger.info("本次共同步了-----"+num+"-----条Erp历史价格数据！");
            return num;
        } else{
            logger.info("此次同步erp价格数据，后台服务没有接受到数据！");
            return 0 ;
        }
    }

    /**
     * 如果此数据已经存在就暂时不处理
     * 暂时不考虑修改的情况
     * 解决的场景：网关不稳定，需要重新同步任务，导致数据重复的场景
     * @param biPriceEntityList
     * @return
     */
    private List<BiPriceEntity> processMultiData(List<BiPriceEntity> biPriceEntityList){
        List<BiPriceEntity> queryList = biPriceDao.selectBatchByList(biPriceEntityList);
        Map<String,BiPriceEntity> queryMap = new HashMap<String, BiPriceEntity>();
        if(null!=queryList&&queryList.size()>0){
            for (BiPriceEntity bi : queryList ) {
                queryMap.put(bi.getVsrcMark(),bi);
            }
        }
        List<BiPriceEntity> insertList = new ArrayList<BiPriceEntity>();
        for (BiPriceEntity bi : biPriceEntityList ) {
            if(bi!=null&&StringUtils.isNotBlank(bi.getVsrcMark())&&!queryMap.containsKey(bi.getVsrcMark())){
                insertList.add(bi);
            }
        }
        return insertList ;
    }

    /**
     * 处理供应商的企业id（云采自己的）
     * @param biPriceEntityList
     */
    private void processSupplyEnterpriseId(List<BiPriceEntity> biPriceEntityList){
        Set<String> supplyIdList = new HashSet<String>();
        for(BiPriceEntity biPriceEntity:biPriceEntityList){
            supplyIdList.add(biPriceEntity.getVsupplyErpId());
        }
        String enterpriseId = biPriceEntityList.get(0).getVpurchaseEnterpriseId();
//        ServiceResponse<Map<String,Long>> serviceResponse = iSupplyDocService.selectSupplyByPurseIdAndPKsup(Long.valueOf(enterpriseId),supplyIdList.toArray(new String[0]));
        ServiceResponse<Map<String,SupplyDocPOJO>> serviceResponse = iSupplyDocService.querySupplyByPurseIdAndPKsup(Long.valueOf(enterpriseId),supplyIdList.toArray(new String[0]));
        if (serviceResponse!=null&&null!=serviceResponse.getResult()){
            Map<String,SupplyDocPOJO> map = serviceResponse.getResult();
            for(BiPriceEntity biPriceEntity:biPriceEntityList){
                SupplyDocPOJO supplyDocPojo=map.get(biPriceEntity.getVsupplyErpId());
                if (supplyDocPojo!= null) {
                    biPriceEntity.setVsupplyEnterpriseId(String.valueOf(supplyDocPojo.getSupplyId()));
                }
            }
        }
    }

    /**
     * 处理供应商的企业id（云采自己的）
     * @param biPriceEntityList
     */
    private void processMaterialYcId(List<BiPriceEntity> biPriceEntityList){
        Set<String> materialIdList = new HashSet<String>();
        for(BiPriceEntity biPriceEntity:biPriceEntityList){
            materialIdList.add(biPriceEntity.getVmaterialErpId());
        }
        String enterpriseId = biPriceEntityList.get(0).getVpurchaseEnterpriseId();
        ServiceResponse<Map<String,Long>> serviceResponse = materialService.selectMaterialByEnterIdAndSources(Long.valueOf(enterpriseId),materialIdList.toArray(new String[0]));
        if (serviceResponse!=null&&null!=serviceResponse.getResult()){
            Map<String,Long> map = serviceResponse.getResult();
            for(BiPriceEntity biPriceEntity:biPriceEntityList){
                biPriceEntity.setVmaterialId(String.valueOf(map.get(biPriceEntity.getVmaterialErpId())));
            }
        }
    }
    private String processUpdateMark(List<BiPriceEntity> biPriceEntityList){
        String tenantId = biPriceEntityList.get(0).getVtenantId();
        String srcMark = biPriceEntityList.get(0).getVsrcMark();
        //得到long类型当前时间
        long l = System.currentTimeMillis();
        //new日期对象
        Date date = new Date(l);
        //转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = dateFormat.format(date);
        String updateMark = tenantId+"@#$"+strDate+"%&"+srcMark;
        for(BiPriceEntity biPriceEntity:biPriceEntityList){
            biPriceEntity.setVupdateMark(updateMark);
        }
        return updateMark;
    }
    /**
     *
     * 查询参数实例
     * [{"id":1430,"supplierId":5,"materialId":636067,"purchaserId":30},{"id":1431,"supplierId":5,"materialId":636065,"purchaserId":30}]
     *
     //        JSONObject jsonObject =new JSONObject(json);
     //        ObjectMapper mapper= new ObjectMapper();
     //        BiPriceEntity biPriceEntity =mapper.readValue(json,BiPriceEntity.class);
     //        String responses = "{'1':{'lowestPrice':100,'averagePrice':100,'lastPrice':100}}";
     //        String responses2 = "{\"1\":{\"lowestPrice\":100,\"averagePrice\":100,\"lastPrice\":100}}";
     * @param json
     * @return
     */
    public String getPrice(String json) {
        if (StringUtils.isBlank(json)){
            logger.error("查询云采历史价格参数为空！");
            return "{}";
        }

        Map<String,String> idKeyMap = new HashMap<String, String>();
        Map<String,BiPriceEntity> keyValueMap = new HashMap<String, BiPriceEntity>();
        JSONArray jsonArray = new JSONArray(json);
        for(int i=0;i<jsonArray.length();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            if(!obj.has("id")){
                logger.debug("该条数据没有id列，该条数据为"+obj.toString());
                continue;
            }
            String id = obj.getString("id");
            logger.error("id="+id);
            String vpurchaseEnterpriseId = obj.getString(BiPriceConstant.param_purchaserId);
            logger.error("vpurchaseEnterpriseId="+vpurchaseEnterpriseId);
            String vsupplyEnterpriseId = obj.getString(BiPriceConstant.param_supplierId);
            logger.error("vsupplyEnterpriseId="+vsupplyEnterpriseId);
            String vmaterialId = obj.getString(BiPriceConstant.param_materialId);
            logger.error("vmaterialId="+vmaterialId);

            List<BiPriceEntity> list = new ArrayList<BiPriceEntity>();
            BiPriceEntity queryBiPriceEntity = new BiPriceEntity();
            queryBiPriceEntity.setVpurchaseEnterpriseId(vpurchaseEnterpriseId);
            //queryBiPriceEntity.setVsupplyEnterpriseId(vsupplyEnterpriseId);
            if(vsupplyEnterpriseId!=null && "".equals(vsupplyEnterpriseId.trim())){
            	queryBiPriceEntity.setVsupplyEnterpriseId(null);
            }else{
            	queryBiPriceEntity.setVsupplyEnterpriseId(vsupplyEnterpriseId);
            }
            
            queryBiPriceEntity.setVmaterialId(vmaterialId);
            list.add(queryBiPriceEntity);

            String key = vpurchaseEnterpriseId+"@#$"+vsupplyEnterpriseId+"@#$"+vmaterialId;
            idKeyMap.put(id,key);
            BiPriceEntity biPriceEntity_avg_min = biPriceDao.selectAvgMinPirce(list);
            if(null==biPriceEntity_avg_min){
                biPriceEntity_avg_min=new BiPriceEntity();
            }
            BiPriceEntity biPriceEntity_last = biPriceDao.selectLastPrice(list);
            if(null!=biPriceEntity_last){
                biPriceEntity_avg_min.setNsum(biPriceEntity_last.getNprice());
            }
            this.processNumTypeValue(new BiPriceEntity[]{biPriceEntity_avg_min});
            keyValueMap.put(key,biPriceEntity_avg_min);
        }

        //组装返回结果
        /**
         * 平均值：nprice
         * 最小值：nmny
         * 上次价格：nsum
         */
        Map<String, Object> keyRs = new HashMap<String, Object>();
        Map<String, Object> valueRs = null;
        for(String id :idKeyMap.keySet()){
            valueRs = new HashMap<String, Object>();
            BiPriceEntity biPriceEntity = keyValueMap.get(idKeyMap.get(id));
            if(null==biPriceEntity){
                valueRs.put(BiPriceConstant.LOWESTPRICE,"");
                valueRs.put(BiPriceConstant.AVERAGEPRICE,"");
                valueRs.put(BiPriceConstant.LASTPRICE,"");
            }else{
                valueRs.put(BiPriceConstant.LOWESTPRICE,null==biPriceEntity.getNmny()?"":biPriceEntity.getNmny());
                //valueRs.put(BiPriceConstant.AVERAGEPRICE,null==biPriceEntity.getNmny()?"":biPriceEntity.getNprice());
                valueRs.put(BiPriceConstant.AVERAGEPRICE,null==biPriceEntity.getNprice()?"":biPriceEntity.getNprice());
                //valueRs.put(BiPriceConstant.LASTPRICE,null==biPriceEntity.getNmny()?"":biPriceEntity.getNsum());
                valueRs.put(BiPriceConstant.LASTPRICE,null==biPriceEntity.getNsum()?"":biPriceEntity.getNsum());
            }
            keyRs.put(id,valueRs);
        }
        try {
//            String rs = new ObjectMapper().writeValueAsString(keyRs);
            return  new ObjectMapper().writeValueAsString(keyRs);
        } catch (Exception e) {
            logger.error("查询云采历史价格错误："+e.getMessage());
            return "{}";
        }
    }

    public void processNumTypeValue(BiPriceEntity[] biPriceEntities){
        if (null!=biPriceEntities&&biPriceEntities.length>0){
            for (BiPriceEntity biPriceEntity:biPriceEntities){
                if(biPriceEntity==null){
                    continue;
                }
                if(null==biPriceEntity.getNmny()||0==biPriceEntity.getNmny().doubleValue()){
                    biPriceEntity.setNmny(null);
                }
                if(null==biPriceEntity.getNprice()||0==biPriceEntity.getNprice().doubleValue()){
                    biPriceEntity.setNprice(null);
                }
                if(null==biPriceEntity.getNsum()||0==biPriceEntity.getNsum().doubleValue()){
                    biPriceEntity.setNsum(null);
                }
            }
        }
    }

    public int insertBatchTempTable(List<BiPriceEntity> biPriceEntityList) {
        return biPriceDao.insertBatchTempTable(biPriceEntityList);
    }

    public BiPriceEntity selectAvgMinPirce2(BiPriceEntity biPriceEntity) {
        return biPriceDao.selectAvgMinPirce2(biPriceEntity);
    }

    public BiPriceEntity selectAvgMinPirce(List<BiPriceEntity> biPriceEntityList) {
        return biPriceDao.selectAvgMinPirce(biPriceEntityList);
    }

    public BiPriceEntity selectLastPrice(List<BiPriceEntity> biPriceEntityList) {
        return biPriceDao.selectLastPrice(biPriceEntityList);
    }

    public List<BiPriceEntity> selectBatchByDefSql(String sql) {
        String sql_limit= sql+" limit  100  offset  0 ";
        logger.info(" 自定义sql查询，只查询前100条！sql语句为： "+sql_limit);
        return biPriceDao.selectBatchByDefSql(sql_limit);
    }

    public List<BiPriceEntity> selectBatchByList(List<BiPriceEntity> biPriceEntityList) {
        return biPriceDao.selectBatchByList(biPriceEntityList);
    }

}
