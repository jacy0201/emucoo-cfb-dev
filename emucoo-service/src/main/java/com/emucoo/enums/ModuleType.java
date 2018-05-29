package com.emucoo.enums;

/**
 * Created by sj on 2018/5/29.
 */
public enum ModuleType {
    COMMON_TASK(1, "常规任务"),

    ARRANGE_TASK(2, "指派任务"),

    IMPROVE_TASK(3, "改善任务"),

    SHOP_PLAN(4, "巡店安排"),

    COMMENT(5, "评论"),

    WORK_MEMORANDUM(6, "工作备忘");

    private final Integer code;
    private final String msg;


    private ModuleType(Integer val, String info) {
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
