package com.emucoo.enums;

/**
 * Created by sj on 2018/4/21.
 */
public enum ReportFinishStatus {
    FINISHED(1, "已完成"),
    NOT_FINISH(2, "未完成");


    private final Integer code;
    private final String msg;


    private ReportFinishStatus(Integer val, String info) {
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
