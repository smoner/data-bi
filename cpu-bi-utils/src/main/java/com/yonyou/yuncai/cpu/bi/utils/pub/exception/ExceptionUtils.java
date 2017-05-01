package com.yonyou.yuncai.cpu.bi.utils.pub.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by fengjqc on 2017/4/8.
 */
public class ExceptionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);
    private ExceptionUtils() {
        // 缺省构造方法
    }

    /**
     * 异常处理工具类的工厂方法。
     *
     * @return 返回异常处理工具类的实例
     * @deprecated 用具体的static方法替代
     */
    @Deprecated
    public static ExceptionUtils getInstance() {
        return new ExceptionUtils();
    }

    /**
     * 将最底层的异常解析出来
     *
     * @param ex 要处理的异养
     * @return 最底层的异常
     */
    public static Throwable unmarsh(Throwable ex) {
        Throwable cause = ex.getCause();
        if (cause != null) {
            cause = ExceptionUtils.unmarsh(cause);
        }
        else {
            cause = ex;
        }
        return cause;
    }

    /**
     * 将异常装载到快速异常通道中向上传递
     *
     * @param ex 要装载的异常
     */
    public static void wrappException(Exception ex) {
        logger.error(ex.getMessage());
        throw new RuntimeException(ex);
    }

    /**
     * 将异常装载到快速异常通道中向上传递
     *
     * @param errormsg 要装载的异常
     */
    public static void wrappException(String errormsg) {
        logger.error(errormsg);
        throw new RuntimeException(errormsg);
    }

    /**
     * 抛出不支持异常
     */
    public static void unSupported() {
        String message = "不支持此种业务，请检查"; /*-=notranslate=-*/
        wrappException(message);
    }

}
