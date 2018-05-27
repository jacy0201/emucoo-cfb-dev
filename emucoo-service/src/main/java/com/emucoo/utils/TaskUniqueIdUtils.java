package com.emucoo.utils;

/**
 * Created by tombaby on 2018/3/30.
 */
public class TaskUniqueIdUtils {
    public static String genUniqueId() {
         return Long.toString(System.currentTimeMillis() * 10000 + Math.round(Math.floor(Math.random() * 10000)));
    }
}
