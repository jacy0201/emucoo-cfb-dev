package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_label")
public class TLabel extends BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标签组id
     */
    @Column(name = "label_group_id")
    private Integer labelGroupId;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签备注
     */
    private String remark;

    /**
     * 标签排序
     */
    private Integer sort;

    /**
     * 是否删除(0：正常，1：删除）
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 创建用户id
     */
    @Column(name = "create_user_id")
    private Long createUserId;

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
     * 数据修改人ID
     */
    @Column(name = "modify_user_id")
    private Long modifyUserId;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取标签组id
     *
     * @return label_group_id - 标签组id
     */
    public Integer getLabelGroupId() {
        return labelGroupId;
    }

    /**
     * 设置标签组id
     *
     * @param labelGroupId 标签组id
     */
    public void setLabelGroupId(Integer labelGroupId) {
        this.labelGroupId = labelGroupId;
    }

    /**
     * 获取标签名称
     *
     * @return name - 标签名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置标签名称
     *
     * @param name 标签名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取标签备注
     *
     * @return remark - 标签备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置标签备注
     *
     * @param remark 标签备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取标签排序
     *
     * @return sort - 标签排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置标签排序
     *
     * @param sort 标签排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取是否删除(0：正常，1：删除）
     *
     * @return is_del - 是否删除(0：正常，1：删除）
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除(0：正常，1：删除）
     *
     * @param isDel 是否删除(0：正常，1：删除）
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取创建用户id
     *
     * @return create_user_id - 创建用户id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建用户id
     *
     * @param createUserId 创建用户id
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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
}