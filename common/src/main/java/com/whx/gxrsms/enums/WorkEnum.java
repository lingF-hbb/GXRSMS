package com.whx.gxrsms.enums;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/2/23
 **/
public enum WorkEnum {
    WORKED(1,"启用"),
    STOP(0, "停用");

    private Integer code;
    private String msg;

    WorkEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
