package com.yonyou.yuncai.cpu.bi.utils.database;

import com.yonyou.yuncai.cpu.bi.utils.pub.AssertUtils;
import com.yonyou.yuncai.cpu.bi.utils.pub.ValueUtils;
import com.yonyou.yuncai.cpu.bi.utils.pub.exception.ExceptionUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

/**
 * Created by fengjqc on 2017/4/8.
 */
public final class RowSet implements IRowSet {

    /**
     * 二维矩阵数据
     */
    private Object[][] datas;

    /**
     * 遍历的游标
     */
    private int cursor = -1;

    /**
     * 构造函数
     *
     * @param datas 二维矩阵数组
     */
    public RowSet(Object[][] datas) {
        this.datas = datas;
    }

    public int getInt(int index) {
        Object obj = this.getObject(index);
        return ValueUtils.getInt(obj, 0);
    }

    public Object getObject(int index) {
        Object object = null;
        Object[] row = this.datas[this.cursor];
        if (row.length <= index) {
            StringBuffer bfer = new StringBuffer();
            bfer.append("out of the rowset's row size ");
            bfer.append(" the row size is:");
            bfer.append(row.length);
            bfer.append(" the index is:");
            bfer.append(index);
            ExceptionUtils.wrappException(bfer.toString());
        }
        object = row[index];
        return object;
    }

    public String getString(int index) {
        String retValue = null;
        Object obj = this.getObject(index);
        retValue = ValueUtils.getString(obj);
        return retValue;
    }

    public Double getDouble(int index) {
        Object obj = this.getObject(index);
        Double returnValue = ValueUtils.getDouble(obj);
        return returnValue;
    }

    public Boolean getBoolean(int index) {
        Object obj = this.getObject(index);
        Boolean retValue = ValueUtils.getBoolean(obj);
        return retValue;
    }

    public Date getDate(int index) {
        Object obj = this.getObject(index);
        Date retValue = ValueUtils.getDate(obj);
        return retValue;
    }

    public Integer getInteger(int index) {
        Object obj = this.getObject(index);
        Integer returnValue = ValueUtils.getInteger(obj);
        return returnValue;
    }


    public Time getTime(int index) {
        Object obj = this.getObject(index);
        Time retValue = ValueUtils.getTime(obj);
        return retValue;
    }

    public BigDecimal getBigDecimal(int index) {
        Object obj = this.getObject(index);
        BigDecimal returnValue = ValueUtils.getBigDecimal(obj);
        return returnValue;
    }


    public boolean next() {
        boolean returnValue = false;
        int size = this.datas.length;
        if ((this.cursor < (size - 1)) && (size > 0)) {
            returnValue = true;
        }
        this.cursor++;
        return returnValue;
    }

    public void reset() {
        this.cursor = -1;
    }

    public int size() {
        return this.datas.length;
    }

    public String[][] toTwoDimensionStringArray() {
        int size = this.datas.length;
        if (size == 0) {
            return new String[0][0];
        }
        Object[] row = this.datas[0];
        int length = row.length;

        String[][] ret = new String[size][length];
        for (int i = 0; i < size; i++) {
            row = this.datas[i];
            for (int j = 0; j < length; j++) {
                ret[i][j] = row[j] == null ? null : row[j].toString();
            }
        }
        return ret;
    }

    public String[] toOneDimensionStringArray() {
        int size = this.datas.length;
        if (size == 0) {
            return new String[0];
        }
        Object[] row = this.datas[0];
        int length = row.length;
        AssertUtils.assertValue(length == 1, "length == 1");

        String[] ret = new String[size];
        for (int i = 0; i < size; i++) {
            row = this.datas[i];
            ret[i] = row[0] == null ? null : row[0].toString();
        }
        return ret;
    }

}
