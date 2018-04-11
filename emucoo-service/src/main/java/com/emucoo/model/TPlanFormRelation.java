package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_plan_form_relation")
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
    private Long formMainId;

    /**
     * 表单引用次数
     */
    @Column(name = "fom_use_count")
    private Long fomUseCount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

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

    /**
     * 获取表单引用次数
     *
     * @return fom_use_count - 表单引用次数
     */
    public Long getFomUseCount() {
        return fomUseCount;
    }

    /**
     * 设置表单引用次数
     *
     * @param fomUseCount 表单引用次数
     */
    public void setFomUseCount(Long fomUseCount) {
        this.fomUseCount = fomUseCount;
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
}