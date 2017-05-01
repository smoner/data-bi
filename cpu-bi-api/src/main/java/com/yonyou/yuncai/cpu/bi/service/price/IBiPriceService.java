package com.yonyou.yuncai.cpu.bi.service.price;

import com.yonyou.yuncai.cpu.bi.domain.price.BiPriceEntity;

import java.util.List;

/**
 * Created by fengjqc on 2017/3/22.
 */
public interface IBiPriceService {
    public int insert(BiPriceEntity biPriceEntity);
    public int insertBatch(List<BiPriceEntity> biPriceEntityList);
    public String getPrice(String json) ;
    public int insertBatchTempTable(List<BiPriceEntity> biPriceEntityList);
    public BiPriceEntity selectAvgMinPirce2(BiPriceEntity biPriceEntity);
    public BiPriceEntity selectAvgMinPirce(List<BiPriceEntity> biPriceEntityList);
    public BiPriceEntity selectLastPrice(List<BiPriceEntity> biPriceEntityList);

    /**
     * 后台没有任何处理逻辑，直接把传过来的sql拿来执行
     * 暂时还没有增加分页处理，所以调用方需要控制查询条件
     * TODO后续增加分页
     * @param sql
     * @return
     */
    public List<BiPriceEntity> selectBatchByDefSql(String sql);
    public List<BiPriceEntity> selectBatchByList(List<BiPriceEntity> biPriceEntityList);
    public void tempTableTest(BiPriceEntity biPriceEntity);
    public int updateByDefSql(String updateSql);
}
