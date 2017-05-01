package com.yonyou.yuncai.cpu.bi.dao.price;

import com.yonyou.yuncai.cpu.bi.domain.price.BiPriceEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fengjqc on 2017/3/22.
 */
@Repository
public interface BiPriceDao {
    public int insert(BiPriceEntity biPriceEntity);
    public int insertBatch(List<BiPriceEntity> biPriceEntityList);
    public int insertBatchTempTable(List<BiPriceEntity> biPriceEntityList);
    public BiPriceEntity selectAvgMinPirce2(BiPriceEntity biPriceEntity);
    public BiPriceEntity selectAvgMinPirce(List<BiPriceEntity> biPriceEntityList);
    public BiPriceEntity selectLastPrice(List<BiPriceEntity> biPriceEntityList);
    public List<BiPriceEntity> selectBatchByDefSql(String sql);
    public List<BiPriceEntity> selectBatchByList(List<BiPriceEntity> biPriceEntityList);
    public int updateByDefSql(String updateSql);
}
