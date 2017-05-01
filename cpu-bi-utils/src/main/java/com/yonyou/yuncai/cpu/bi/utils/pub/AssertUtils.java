package com.yonyou.yuncai.cpu.bi.utils.pub;

/**
 * Created by fengjqc on 2017/4/8.
 */
public class AssertUtils {
    private AssertUtils() {
        // 缺省构造方法
    }

    /**
     * 断言工具类的工厂方法。
     *
     * @return 返回断言工具类的实例
     * @deprecated 用具体的static方法替代
     */
    @Deprecated
    public static AssertUtils getInstance() {
        return new AssertUtils();
    }

    /**
     * 对当前的条件进行断言处理。如果传入的flag参数不为true。则将expression作为异常的内容抛出
     *
     * @param flag 要判断的条件
     * @param expression 出现异常时要包含在异常中的内容
     */
    public static void assertValue(boolean flag, String expression) {
        if (!flag) {
            String message = "the argument value is not valid,the expression is: ";
            message += expression;
            throw new IllegalArgumentException(message);
        }
    }


}
