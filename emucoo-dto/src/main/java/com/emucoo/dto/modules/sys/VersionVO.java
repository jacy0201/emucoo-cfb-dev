package com.emucoo.dto.modules.sys;

import lombok.Data;

/**
 * @author: jacy
 */
@Data
public class VersionVO {

    //版本号
    private String version;

    /**
     * app名称
     */
    private String appName;

    //app下载地址
    private String appUrl;

    //是否强制更新
    private Boolean isUpdateInstall;

    //备注
    private String remark;


}
