package com.emucoo.enums;

/**
 * Created by sj on 2018/5/23.
 */
public enum FormType {
    RVR_TYPE(1, "类RVR表"),

    ABILITY_TYPE(2, "能力模型表"),

    RYG_TYPE(3,"红黄绿表");

    private final Integer code;
    private final String msg;


    private FormType(Integer val, String info) {
        this.code = val;
        this.msg = info;

    }

    // 普通方法
    public static String getName(int index) {
        for (FormType c : FormType.values()) {
            if (c.getCode() == index) {
                return c.msg;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
