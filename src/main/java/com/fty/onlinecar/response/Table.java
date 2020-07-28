package com.fty.onlinecar.response;

import java.util.List;

/**
 * Created by Administrator on 2017/12/31.
 */
public class Table {
   private int code;
   private String msg;
   private int count;
   private List data;

    public Table(int count, List data) {
        this.count = count;
        this.data = data;
    }

    public Table(String msg, int count, List data) {
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public Table(int code, String msg, int count, List data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public Table() {
        super();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
