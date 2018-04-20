package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_plan_form_relation")
@ApiModel
public class TPlanFormRelation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 计划id
     */
    @Column(name = "plan_id")
    private Long planId;

    /**
     * 表单id
     */
    @Column(name = "form_main_id")
    @ApiModelProperty(name = "formMainId", value = "添加的表单id")
    private Long formMainId;

    @Transient
    private String name;

    /**
     * 表单引用次数
     */
    @Column(name = "form_use_count")
    @ApiModelProperty(name = "formUseCount", value = "该表单周期内使用次数")
    private Long formUseCount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    private Boolean isDel;

    private Boolean isUse;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取计划id
     *
     * @return plan_id - 计划id
     */
    public Long getPlanId() {
        return planId;
    }

    /**
     * 设置计划id
     *
     * @param planId 计划id
     */
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    /**
     * 获取表单id
     *
     * @return form_main_id - 表单id
     */
    public Long getFormMainId() {
        return formMainId;
    }

    /**
     * 设置表单id
     *
     * @param formMainId 表单id
     */
    public void setFormMainId(Long formMainId) {
        this.formMainId = formMainId;
    }

    public Long getFormUseCount() {
        return formUseCount;
    }

    public void setFormUseCount(Long formUseCount) {
        this.formUseCount = formUseCount;
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
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }
}