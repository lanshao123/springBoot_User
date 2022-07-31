package com.wh.entity;

import java.io.Serializable;

public class Result implements Serializable {
    private Integer code;//返回的业务码  0：成功执行  1：发生错误
    private String message;//信息
    private String token;

    public String getToken() {
        return token;
    }

    public Result(Integer code, String message, String token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.code = 0;
        this.message = "执行成功";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}