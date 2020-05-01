package com.summer.common.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = 4636041224949017469L;

    private String status;
    private String message;
    private Object data;

    public static<T> Result ok(T t){
        return new Result(ResponseEnum.SUCCESS, t);
    }

    public static Result ok(String msg){
        return new Result(ResponseEnum.EXCEPTION.getStatus(), msg, null);
    }

    public static<T> Result error(T t){
        return new Result(ResponseEnum.EXCEPTION,t);
    }

    public static Result error(String msg){
        return new Result(ResponseEnum.EXCEPTION.getStatus(), msg, null);
    }


    public static<T> Result res(ResponseEnum resp, T t){
        return new Result(resp, t);
    }

    private Result(ResponseEnum response, Object o) {
        this.status = response.getStatus();
        this.message = response.getMessage();
        this.data = o;
    }

    private Result(String status, String message, Object e) {
        this.status = status;
        this.message = message;
        this.data = e;
    }
}
