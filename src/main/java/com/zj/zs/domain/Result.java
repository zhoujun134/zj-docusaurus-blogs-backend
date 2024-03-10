package com.zj.zs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private String code;

    private String message;

    private T data;

    public static Result<Object> ok() {
        Result<Object> result = new Result<Object>();
        result.setCode("0");
        return result;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode("0");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.setCode("-1");
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode("-1");
        result.setMessage("System.error");
        return result;
    }

}
