package com.wd.mqmodel.common.pojo;


public enum ErrorCode {
    MY_ERROR(9000, ""),

    SYSTEM_ERROR(999999, "接口异常");

    private Integer code;
    private String message;

    private ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
