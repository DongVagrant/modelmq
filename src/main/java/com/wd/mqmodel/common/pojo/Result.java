package com.wd.mqmodel.common.pojo;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = -5706503727303146895L;
    private T obj;
    private Boolean success = true;
    private Integer errorCode = 0;
    private String errorMessage = "";

    public Result() {
    }

    public Result(T obj) {
        this.obj = obj;
    }

    public Result(T obj, Boolean success) {
        this.obj = obj;
        this.success = success;
    }

    public Result(Boolean success) {
        this.success = success;
    }

    public Result(ErrorCode errorCode) {
        this.success = false;
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }

    public Result(ErrorCode errorCode, String msg) {
        this.success = false;
        if (errorCode != null) {
            this.errorCode = errorCode.getCode();
            this.errorMessage = errorCode.getMessage();
        } else {
            this.errorCode = 0;
            this.errorMessage = "";
        }

        if (msg != null) {
            if (!this.errorMessage.isEmpty()) {
                this.errorMessage = this.errorMessage + " ";
            }

            this.errorMessage = this.errorMessage + msg;
        }

    }

    public T getObj() {
        return this.obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

//    public String toString() {
//        return JacksonUtil.toJSon(this);
//    }
}
