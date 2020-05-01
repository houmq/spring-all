package com.summer.common.util;

public enum  ResponseEnum {

    ERROR("4000", "失败"),
    SUCCESS("2000", "成功"),
    EXCEPTION("4004", "错误"),
    UNAUTHORIZED("4003","权限不足"),
    NOSIGNIN("40005", "未登录");


    private String status;
    private String message;

    ResponseEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
