package com.fty.onlinecar.response;


import com.fty.onlinecar.utils.JSONUtils;

/**
 * 统一API响应结果封装
 */
public class Result {
    private int code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(Exception e) {
        this.code=400;
        this.message = e.getMessage();
    }

    public Result(IResultEnum resultEnum, Object data) {
        this.data = data;
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public Result(IResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = null;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return JSONUtils.obj2json(this);
    }
}
