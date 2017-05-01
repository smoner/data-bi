package com.yonyou.yuncai.cpu.bi.utils.database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

/**
 * Created by fengjqc on 2017/4/8.
 */
public interface IRowSet extends Serializable {

    /**
     * 获取int值
     *
     * @param index 索引位值
     * @return 当前索引对应的int值
     */
    int getInt(int index);

    /**
     * 获取String值
     *
     * @param index 索引位值
     * @return 当前索引对应的String值
     */
    String getString(int index);

    /**
     * 获取Double值
     *
     * @param index 索引位值
     * @return 当前索引对应的Double值
     */
    Double getDouble(int index);


    /**
     * 获取Time值
     *
     * @param index 索引位值
     * @return 当前索引对应的Time值
     */
    Time getTime(int index);

    /**
     * 获取Boolean值
     *
     * @param index 索引位值
     * @return 当前索引对应的Boolean值
     */
    Boolean getBoolean(int index);

    /**
     * 获取Date值
     *
     * @param index 索引位值
     * @return 当前索引对应的Date值
     */
    Date getDate(int index);

    /**
     * 获取Integer值
     *
     * @param index 索引位值
     * @return 当前索引对应的Integer值
     */
    Integer getInteger(int index);

    /**
     * 获取BigDecimal值
     *
     * @param index 索引位值
     * @return 当前索引对应的BigDecimal值
     */
    BigDecimal getBigDecimal(int index);


    /**
     * 获取Object值
     *
     * @param index 索引位值
     * @return 当前索引对应的Object值
     */
    Object getObject(int index);

    /**
     * 将游标移动到下一行
     *
     * @return 还有下一行时返回真
     */
    boolean next();

    /**
     * 将游标重置到第一行数据前
     */
    void reset();

    /**
     * 获取数据的行数
     *
     * @return 数据的行数
     */
    int size();

    /**
     * 将当前rowset的内容转换为二维字符串数组
     *
     * @return 二维字符串数组
     */
    String[][] toTwoDimensionStringArray();

    /**
     * 将当前rowset的内容转换为一维字符串数组
     *
     * @return 一维字符串数组
     */
    String[] toOneDimensionStringArray();

}
