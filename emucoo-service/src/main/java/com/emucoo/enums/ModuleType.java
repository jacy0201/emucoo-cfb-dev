package com.emucoo.enums;

/**
 * Created by sj on 2018/5/29.
 */
public enum ModuleType {
    COMMON_TASK(1, "常规任务"),

    ARRANGE_TASK(2, "指派任务"),

    IMPROVE_TASK(3, "改善任务"),

    SHOP_PLAN(4, "巡店安排"),

    WORK_MEMORANDUM(5, "工作备忘"),

    COMMENT(6, "评论"),

    REPAIR_TASK(7, "维修任务");

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
