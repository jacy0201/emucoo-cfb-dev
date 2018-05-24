package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_opportunity")
public class TOpportunity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 类型（0：正常，1：其他）
     */
    private Integer type;

    /**
     * 机会点名称
     */
    private String name;

    /**
     * 是否删除(0：正常，1：删除）
     */
    @Column(name = "is_del")
    private Boolean isDel;


    /**
     * 是否启用：0=停用， 1=启用
     */
    @Column(name = "is_use")
    private Boolean isUse;

    /**
     * 创建来源类型：1=前端创建， 2=后台创建
     */
    @Column(name = "create_type")
    private Integer createType;

    /**
     * 机会点描述
     */
    private String description;

    /**
     * 前端能否创建(0：不能，1：能)
     */
    @Column(name = "front_can_create")
    private Boolean frontCanCreate;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建用户id
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 数据修改人ID
     */
    @Column(name = "modify_user_id")
    private Long modifyUserId;

    @Column(name = "org_id")
    private Long orgId;

    @Transient
    private TFormOppt formOppt;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取类型（0：正常，1：其他）
     *
     * @return type - 类型（0：正常，1：其他）
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型（0：正常，1：其他）
     *
     * @param type 类型（0：正常，1：其他）
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取机会点名称
     *
     * @return name - 机会点名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置机会点名称
     *
     * @param name 机会点名称
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取机会点描述
     *
     * @return description - 机会点描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置机会点描述
     *
     * @param description 机会点描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取前端能否创建(0：不能，1：能)
     *
     * @return front_can_create - 前端能否创建(0：不能，1：能)
     */
    public Boolean getFrontCanCreate() {
        return frontCanCreate;
    }

    /**
     * 设置前端能否创建(0：不能，1：能)
     *
     * @param frontCanCreate 前端能否创建(0：不能，1：能)
     */
    public void setFrontCanCreate(Boolean frontCanCreate) {
        this.frontCanCreate = frontCanCreate;
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
     * @return org_id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }


    public Boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean use) {
        isUse = use;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public TFormOppt getFormOppt() {
        return formOppt;
    }

    public void setFormOppt(TFormOppt formOppt) {
        this.formOppt = formOppt;
    }
}