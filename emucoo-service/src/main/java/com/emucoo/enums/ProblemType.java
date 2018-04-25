package com.emucoo.enums;

/**
 * Created by sj on 2018/4/25.
 */
public enum ProblemType {
    NOT_SAMPLE(1, "不抽样"),

    SAMPLING(2, "抽样");

    private final Integer code;
    private final String msg;


    private ProblemType(Integer val, String info) {
        this.code = val;
        this.msg = info;

    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
