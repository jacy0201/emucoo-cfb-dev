package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_app_version")
public class SysAppVersion extends BaseEntity {
    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * app名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 1-ios;2-android
     */
    @Column(name = "app_type")
    private Integer appType;

    /**
     * 版本号
     */
    @Column(name = "app_version")
    private String appVersion;


    /**
     * app 下载地址
     */
    @Column(name = "app_url")
    private String appUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否强制更新
     */
    @Column(name = "is_update_install")
    private Boolean isUpdateInstall;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 1-已删除；0-未删除
     */
    @Column(name = "is_del")
    private Boolean isDel;


    /**
     * 1-启用；0-停用
     */
    @Column(name = "is_use")
    private Boolean isUse;

    public Boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    /**
     * 获取PK
     *
     * @return id - PK
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置PK
     *
     * @param id PK
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取app名称
     *
     * @return app_name - app名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置app名称
     *
     * @param appName app名称
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取1-ios;2-android
     *
     * @return app_type - 1-ios;2-android
     */
    public Integer getAppType() {
        return appType;
    }

    /**
     * 设置1-ios;2-android
     *
     * @param appType 1-ios;2-android
     */
    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    /**
     * 获取版本号
     *
     * @return app_version - 版本号
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置版本号
     *
     * @param appVersion 版本号
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 获取app 下载地址
     *
     * @return app_url - app 下载地址
     */
    public String getAppUrl() {
        return appUrl;
    }

    /**
     * 设置app 下载地址
     *
     * @param appUrl app 下载地址
     */
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取是否强制更新
     *
     * @return is_update_install - 是否强制更新
     */
    public Boolean getIsUpdateInstall() {
        return isUpdateInstall;
    }

    /**
     * 设置是否强制更新
     *
     * @param isUpdateInstall 是否强制更新
     */
    public void setIsUpdateInstall(Boolean isUpdateInstall) {
        this.isUpdateInstall = isUpdateInstall;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return create_user_id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取1-已删除；0-未删除
     *
     * @return is_del - 1-已删除；0-未删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置1-已删除；0-未删除
     *
     * @param isDel 1-已删除；0-未删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}