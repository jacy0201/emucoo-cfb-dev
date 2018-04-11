package com.emucoo.common.util;

import com.xiaoleilu.hutool.date.DateUtil;

import java.util.Date;

/**
 * @package: com.emucoo.common.util
 * @description: 产品编号生产工具
 * @author: fujg
 * @date: 2017/8/28 14:19
 * @version: V1.0.0
 */
public class productCodeUtil {

    private volatile static productCodeUtil PRODUCT_CODE_UTIL;

    public static synchronized productCodeUtil getInstance(){
        if(null == PRODUCT_CODE_UTIL){
            synchronized(productCodeUtil.class){
                if(null == PRODUCT_CODE_UTIL){
                    PRODUCT_CODE_UTIL = new productCodeUtil();
                }
            }
        }
        return PRODUCT_CODE_UTIL;
    }

    private productCodeUtil(){}

    /**
     * 获取私募产品编号
     * @return
     */
    public static String getPriPrdCode(){
        return "P" + getInstance().generatorCode();
    }

    /**
     * 获取内固收产品编号
     * @return
     */
    public static String getFixPrdCode(){
        return "F" + getInstance().generatorCode();
    }

    /**
     * 产生随机编号
     * @return
     */
    private String generatorCode(){
        Date date = new Date();
        return DateUtil.format(date, "yyyyMMddHHmmssSSS");
    }
}
