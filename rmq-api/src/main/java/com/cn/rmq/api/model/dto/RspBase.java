package com.cn.rmq.api.model.dto;

import com.cn.rmq.api.model.Constants;

import java.io.Serializable;

/**
 * <p>Title: RspBase</p>
 * <p>Description: Http操作结果对象</p>
 * @author Chen Nan
 * @date 2019/3/11.
 */
public class RspBase implements Serializable {
    private int code;
    private String msg = Constants.MSG_SUCCESS;
    private Object data;

    public RspBase() {
    }

    public RspBase code(final int code) {
        this.code = code;
        return this;
    }

    public RspBase msg(final String msg) {
        this.msg = msg;
        return this;
    }

    public RspBase data(final Object data) {
        this.data = data;
        return this;
    }

    public RspBase(int code, String msg) {
        this.code = code;
        this.msg = msg;
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


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RspBase{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
