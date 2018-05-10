package com.emucoo.dto.modules.sys;

import lombok.Data;

/**
 * @author: jacy
 */
@Data
public class VersionQuery {

    //版本号
    private String version;

    //1-ios;2-android
    private Integer appType;

}
