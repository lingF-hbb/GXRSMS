package com.whx.gxrsms.bean;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/2/22
 **/
public class Result {

    private static final String SUCCESS = "操作成功";
    private static final String FAIL = "操作失败";

    private int code;
    private String msg;
    private Object data;

    public static Result success() {
        Result result = new Result();
        result.setCode(100);
        result.setMsg(SUCCESS);
        return result;
    }

    public void setFail() {
        this.code = 200;
        this.msg = FAIL;
    }

    public void setFail(String msg) {
        this.code = 200;
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
}
