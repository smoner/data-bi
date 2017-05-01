package com.yonyou.yuncai.cpu.bi.controller.price;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.cpu.api.privilege.enterprise.IEnterpriseService;
import com.yonyou.cpu.domain.criteria.enterprise.EnterpriseCriteria;
import com.yonyou.cpu.domain.entity.enterprise.EnterprisePOJO;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.yuncai.cpu.bi.domain.price.BiPriceConstant;
import com.yonyou.yuncai.cpu.bi.domain.price.BiPriceEntity;
import com.yonyou.yuncai.cpu.bi.service.price.IBiPriceService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.cpu.common.utils.json.JsonUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by fengjqc on 2017/3/23.
 */
@Controller
@RequestMapping(value = "/biPrice")
public class BiPriceController {
    private static final Logger logger = LoggerFactory.getLogger(BiPriceController.class);

    @Autowired
    IBiPriceService iBiPriceService;

    @Autowired
    IEnterpriseService enterpriseService ;

    public EnterprisePOJO getEnterpriseInfo(String tenantId){
        EnterpriseCriteria criteria = new EnterpriseCriteria();
        if(tenantId != null){
            criteria.setTenantid(tenantId);
        }else{
            return null;
        }
        List<EnterprisePOJO> entList = enterpriseService.queryEnterpriseByCriteria(criteria);
        if(entList==null || entList.size()<1){
            return null;
        }
        return entList.get(0);
//        return new EnterprisePOJO();
    }


    /**
     *
     * @param request
     * @param requestbody
     * 1、传入的参数必须是json数组格式
     * 2、json每个列的属性必须有个id属性，作为返回值（json）的key
     * 3、
     *
     *
     * http://yc.yonyou.com/yuncai/cpu-bi/biPrice/query/getPrice
     * @return
     */
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/query/getPrice",method = RequestMethod.GET)
    public @ResponseBody
    String getPrice(HttpServletRequest request, String requestbody) {
        Map<String, Object> keyRs = new HashMap<String, Object>();
        Map<String, Object> valueRs = new HashMap<String, Object>();
        valueRs.put(BiPriceConstant.LOWESTPRICE,100);
        valueRs.put(BiPriceConstant.AVERAGEPRICE,150);
        valueRs.put(BiPriceConstant.LASTPRICE,200);
        keyRs.put("1",valueRs);
        String responses = "{'1':{'lowestPrice':100,'averagePrice':100,'lastPrice':100}}";
        String responses2 = "{\"1\":{\"lowestPrice\":100,\"averagePrice\":100,\"lastPrice\":100}}";
        try {
            JSONObject jasonObject = JSONObject.fromObject(requestbody);
            return new ObjectMapper().writeValueAsString(keyRs);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "{}";
        }
    }

    /**
     * http://yc.yonyou.com/yuncai/cpu-bi/biPrice/query/selectbatchbydefsql?defsql=select * from cpu_biprice where id = 1575
     * @param request
     * @return
     */
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/query/selectbatchbydefsql",method = RequestMethod.GET)
    public @ResponseBody String selectBatchByDefSql(HttpServletRequest request){
        String sql = request.getParameter("defsql");
        try{
            if(StringUtils.isNotBlank(sql)){
                List<BiPriceEntity> biPriceEntityList= iBiPriceService.selectBatchByDefSql(sql);
                logger.info(" 自定义sql查询结束，只查询前100条！sql语句为： "+sql);
                if(biPriceEntityList!=null&&biPriceEntityList.size()>0){
                    String resultString= JsonUtils.toJson(biPriceEntityList).toString();
                    return resultString;
                }
            }
        }catch (Exception e){
            logger.error(" 自定义sql查询失败！sql语句为： "+sql);
            logger.error(" 自定义sql查询失败！日志为： "+e.getMessage());
        }
        return new JSONObject().toString();
    }

    /**
     *
     * 同步过来的数据格式
     * [
     {
     "dataversion": 0,
     "dataenable": 0,
     "id": "1001E410000000003T1V",
     "pk_supplier": "1001E410000000003SNZ",
     "pk_org": "0001E410000000002X7S",
     "pk_material": "1001E410000000003NJY",
     "dbilldate": "2017-01-05 23:08:01",
     "norigtaxprice": 1100,
     "_id": "58d60d415f505351b2a74244"
     },
     {
     "dataversion": 0,
     "dataenable": 0,
     "id": "1001E410000000003T1Y",
     "pk_supplier": "1001E410000000003SNZ",
     "pk_org": "0001E410000000002X7S",
     "pk_material": "1001E410000000003NJY",
     "dbilldate": "2017-01-05 23:11:12",
     "norigtaxprice": 1100,
     "_id": "58d60d415f505351b2a74245"
     }
     ]
     * @param request
     * @param response
     * @param params
     * @return
     * @throws IOException
     * @throws Exception
     */
    @RequestMapping(value = "/insertBatch/insertErpPrice", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public String insertErpPrice(HttpServletRequest request, HttpServletResponse response,
                               @RequestBody Map<String, String> params) throws IOException, Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            List< BiPriceEntity>  list = new ArrayList<BiPriceEntity>();
            String _tenantId = InvocationInfoProxy.getTenantid();
            String erpPriceData = params.get("data");
            if(StringUtils.isNotBlank(erpPriceData)){
                String tenantId = params.get("tenantId");
                EnterprisePOJO enterprisePOJO = getEnterpriseInfo(tenantId);
                String enterpriseId = String.valueOf(enterprisePOJO.getId());
                logger.info("ERP物料信息同步，取网关数据：租户ID："+tenantId);
                JSONArray jsonArray = new JSONArray(erpPriceData);
                BiPriceEntity biPriceEntity = null;
                for(int i=0;i<jsonArray.length();i++){
                    org.json.JSONObject obj = jsonArray.getJSONObject(i);
                    biPriceEntity = new BiPriceEntity();
                    biPriceEntity.setVpurchaseEnterpriseId(enterpriseId);
                    biPriceEntity.setVtenantId(tenantId);
                    //处理单独列值
                    setBiPriceEntityRowValue(biPriceEntity,obj);
                    //处理Json列值
                    setBiPriceEntityJsonValue(biPriceEntity,obj);
                    list.add(biPriceEntity);
                }
                iBiPriceService.insertBatch(list);
            }
                result.put("result", 1);
                result.put("msg", "ERP价格数据同步成功！");
                logger.info("ERP价格数据同步成功");
        }catch(Exception e){
            result.put("status", 0);
            result.put("result", "failed");
            result.put("code", "UNKNOW");
            result.put("msg", e.getMessage());
            logger.error("ERP价格数据同步失败!" + e.getMessage(), e);
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    /**
     * 处理单独列值
     * @param biPriceEntity
     * @param obj
     */
    private void setBiPriceEntityRowValue(BiPriceEntity biPriceEntity,org.json.JSONObject obj){
        if(obj.has(BiPriceConstant.Erp_Id)){
            biPriceEntity.setVsrcMark(obj.getString(BiPriceConstant.Erp_Id));
        }
        if(obj.has(BiPriceConstant.Erp_Pk_supplier)) {
            biPriceEntity.setVsupplyErpId(obj.getString(BiPriceConstant.Erp_Pk_supplier));
        }
        if(obj.has(BiPriceConstant.Erp_Pk_material)) {
            biPriceEntity.setVmaterialErpId(obj.getString(BiPriceConstant.Erp_Pk_material));
        }
        if(obj.has(BiPriceConstant.Erp_Dbilldate)) {
            biPriceEntity.setDbilldate(obj.getString(BiPriceConstant.Erp_Dbilldate));
        }
        if(obj.has(BiPriceConstant.Erp_Norigtaxprice)) {
            biPriceEntity.setNprice(obj.getBigDecimal(BiPriceConstant.Erp_Norigtaxprice));
        }


        if(obj.has(BiPriceConstant.Erp_Nsum)) {
            biPriceEntity.setNsum(obj.getBigDecimal(BiPriceConstant.Erp_Nsum));
        }
        if(obj.has(BiPriceConstant.Erp_Nmny)) {
            biPriceEntity.setNmny(obj.getBigDecimal(BiPriceConstant.Erp_Nmny));
        }
        if(obj.has(BiPriceConstant.Erp_NpriceNoTax)) {
            biPriceEntity.setNpriceNoTax(obj.getBigDecimal(BiPriceConstant.Erp_NpriceNoTax));
        }
        if(obj.has(BiPriceConstant.Erp_Ntax)) {
            biPriceEntity.setNtax(obj.getBigDecimal(BiPriceConstant.Erp_Ntax));
        }
        if(obj.has(BiPriceConstant.Erp_NmnyNoTax)) {
            biPriceEntity.setNmnyNoTax(obj.getBigDecimal(BiPriceConstant.Erp_NmnyNoTax));
        }


        if(obj.has(BiPriceConstant.Erp_Vsrc)) {
            biPriceEntity.setVsrc(obj.getString(BiPriceConstant.Erp_Vsrc));
        }
        if(obj.has(BiPriceConstant.Erp_VunitName)) {
            biPriceEntity.setVunitName(obj.getString(BiPriceConstant.Erp_VunitName));
        }
        if(obj.has(BiPriceConstant.Erp_Vcurrency)) {
            biPriceEntity.setVcurrency(obj.getString(BiPriceConstant.Erp_Vcurrency));
        }
        if(obj.has(BiPriceConstant.Erp_VsrcSystem)) {
            biPriceEntity.setVsrcSystem(obj.getString(BiPriceConstant.Erp_VsrcSystem));
        }
        if(obj.has(BiPriceConstant.Erp_VaddType)) {
            biPriceEntity.setVaddType(obj.getString(BiPriceConstant.Erp_VaddType));
        }
        if(obj.has(BiPriceConstant.Erp_VsrcBillCode)) {
            biPriceEntity.setVsrcBillCode(obj.getString(BiPriceConstant.Erp_VsrcBillCode));
        }
    }

    /**
     * 处理Json列值
     * @param biPriceEntity
     * @param obj
     */
    private void setBiPriceEntityJsonValue(BiPriceEntity biPriceEntity,org.json.JSONObject obj){
        if(obj!=null&&obj.length()>0){
            Set<String> keySet=obj.keySet();
            if(keySet!=null&&keySet.size()>0){
                for (String key:keySet) {
                    if(StringUtils.isBlank(key)){
                        continue;
                    }
                    if (key.startsWith(BiPriceConstant.ERP_PREFIX_PURCHASE)){
                        Map<String, Object> newjson = this.processJSONObjectValue(obj,biPriceEntity.getJpurchase(),key);
                        biPriceEntity.setJpurchase(newjson);
                    }
                    if (key.startsWith(BiPriceConstant.ERP_PREFIX_SUPPLY)){
                        Map<String, Object> newjson = this.processJSONObjectValue(obj,biPriceEntity.getJsupply(),key);
                        biPriceEntity.setJsupply(newjson);
                    }
                    if (key.startsWith(BiPriceConstant.ERP_PREFIX_MATERIAL)){
                        Map<String, Object> newjson = this.processJSONObjectValue(obj,biPriceEntity.getJmaterial(),key);
                        biPriceEntity.setJmaterial(newjson);
                    }
                    if (key.startsWith(BiPriceConstant.ERP_PREFIX_CONDITION)){
                        Map<String, Object> newjson = this.processJSONObjectValue(obj,biPriceEntity.getJcondition(),key);
                        biPriceEntity.setJcondition(newjson);
                    }
                    if (key.startsWith(BiPriceConstant.ERP_PREFIX_PRICE)){
                        Map<String, Object> newjson = this.processJSONObjectValue(obj,biPriceEntity.getJprice(),key);
                        biPriceEntity.setJprice(newjson);
                    }
                }
            }
        }
    }

    private Map<String, Object> processJSONObjectValue(org.json.JSONObject obj,Map<String, Object> jsonv,String key){
        if (jsonv==null){
            jsonv = new HashMap<String, Object>();
        }
        jsonv.put(key,obj.get(key));
        return jsonv;
    }


    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/query/getPrice2",method = RequestMethod.GET)
    @ResponseBody
    public   String getPrice2(HttpServletRequest request) {
//        Map<String, Object> keyRs = new HashMap<String, Object>();
//        Map<String, Object> valueRs = new HashMap<String, Object>();
//        valueRs.put(BiPriceConstant.LOWESTPRICE,100);
//        valueRs.put(BiPriceConstant.AVERAGEPRICE,150);
//        valueRs.put(BiPriceConstant.LASTPRICE,200);
//        keyRs.put("1",valueRs);
//        String responses = "{'1':{'lowestPrice':100,'averagePrice':100,'lastPrice':100}}";
//        String responses2 = "{\"1\":{\"lowestPrice\":100,\"averagePrice\":100,\"lastPrice\":100}}";

        try {
            List<BiPriceEntity> list = new ArrayList<BiPriceEntity>();
            BiPriceEntity biPriceEntity = new BiPriceEntity();
            String name = new Date().toString();
            biPriceEntity.setVmaterial(name);
            list.add(biPriceEntity);
               iBiPriceService.insertBatch(list);
            return JsonUtils.toJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "{}";
        }
    }
}
