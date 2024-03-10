package com.zj.zs.utils.exception;

/**
 * @ClassName BusinessException
 * @Author zj
 * @Description
 * @Date 2024/3/10 15:34
 * @Version v1.0
 **/
public class BusinessException {

    public static void exception(boolean flag, String message) {
        if (!flag) {
            return;
        }
        throw new RuntimeException(message);
    }
}
