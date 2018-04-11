package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_img_append")
public class TImgAppend extends BaseEntity {
    /**
     * 主键PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 图片来源 1：后台上传，2：前台上传
     */
    private Boolean source;

    /**
     * 图片原名称
     */
    @Column(name = "orginal_name")
    private String orginalName;

    /**
     * 图片保存名
     */
    @Column(name = "saved_name")
    private String savedName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 数据创建人ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 数据修改人ID
     */
    @Column(name = "modify_user_id")
    private Long modifyUserId;

    /**
     * 是否逻辑删除0：正常1：逻辑删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 图片路径
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 获取主键PK
     *
     * @return id - 主键PK
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键PK
     *
     * @param id 主键PK
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取图片来源 1：后台上传，2：前台上传
     *
     * @return source - 图片来源 1：后台上传，2：前台上传
     */
    public Boolean getSource() {
        return source;
    }

    /**
     * 设置图片来源 1：后台上传，2：前台上传
     *
     * @param source 图片来源 1：后台上传，2：前台上传
     */
    public void setSource(Boolean source) {
        this.source = source;
    }

    /**
     * 获取图片原名称
     *
     * @return orginal_name - 图片原名称
     */
    public String getOrginalName() {
        return orginalName;
    }

    /**
     * 设置图片原名称
     *
     * @param orginalName 图片原名称
     */
    public void setOrginalName(String orginalName) {
        this.orginalName = orginalName;
    }

    /**
     * 获取图片保存名
     *
     * @return saved_name - 图片保存名
     */
    public String getSavedName() {
        return savedName;
    }

    /**
     * 设置图片保存名
     *
     * @param savedName 图片保存名
     */
    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取数据创建人ID
     *
     * @return create_user_id - 数据创建人ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置数据创建人ID
     *
     * @param createUserId 数据创建人ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取数据修改人ID
     *
     * @return modify_user_id - 数据修改人ID
     */
    public Long getModifyUserId() {
        return modifyUserId;
    }

    /**
     * 设置数据修改人ID
     *
     * @param modifyUserId 数据修改人ID
     */
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    /**
     * 获取是否逻辑删除0：正常1：逻辑删除
     *
     * @return is_del - 是否逻辑删除0：正常1：逻辑删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置是否逻辑删除0：正常1：逻辑删除
     *
     * @param isDel 是否逻辑删除0：正常1：逻辑删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取图片路径
     *
     * @return img_url - 图片路径
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置图片路径
     *
     * @param imgUrl 图片路径
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}