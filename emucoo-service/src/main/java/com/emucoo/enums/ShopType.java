package com.emucoo.enums;

/**
 * Created by sj on 2018/6/1.
 */
public enum ShopType {
    DIRECT_SALE(1, "直营"),

    FRANCHISE(2, "加盟");

    private final Integer code;
    private final String msg;


    private ShopType(Integer val, String info) {
        this.code = val;
        this.msg = info;

    }

    // 普通方法
    public static String getName(int code) {
        for (ShopType c : ShopType.values()) {
            if (c.getCode() == code) {
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
