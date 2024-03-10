package com.zj.zs.config.interceptor;

import com.zj.zs.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 全局异常拦截
    @ExceptionHandler
    public Result<Object> handlerException(Exception exception) {
        log.error("GlobalExceptionHandler######handlerException: exception message:{}",
                exception.getMessage(), exception);
        return Result.fail(exception.getMessage());
    }
}
