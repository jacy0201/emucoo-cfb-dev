package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "t_loop_plan")
@ApiModel()
public class TLoopPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务计划名
     */
    @ApiModelProperty(name = "name", value = "计划名称")
    private String name;

    /**
     * 描述说明
     */
    @ApiModelProperty(name = "description", value = "描述")
    private String description;

    /**
     * 所选择的部门ID
     */
    @Column(name = "dpt_id")
    @ApiModelProperty(name = "dptId", value = "部门id")
    private Long dptId;

    /**
     * 状态 0：启用 1：停用
     */
    @ApiModelProperty(name = "status", value = "状态",example = "状态 0：启用 1：停用")
    private Boolean status;

    /**
     * 计划开始月份
            1：月循环
            2：季度循环
            3：年循环
     */
    @Column(name = "plan_start_date")
    @ApiModelProperty(name = "planStartDate", value = "计划期间开始日期")
    private Date planStartDate;

    /**
     * 计划结束月份
     */
    @Column(name = "plan_end_date")
    @ApiModelProperty(name = "planEndDate", value = "计划期间结束日期")
    private Date planEndDate;

    /**
     * 巡店周期(月)
     */
    @Column(name = "plan_cycle")
    @ApiModelProperty(name = "planCycle", value = "计划周期")
    private Integer planCycle;

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

    @Transient
    @ApiModelProperty(name = "planFormRelationList", value = "计划添加的表单数组")
    List<TPlanFormRelation> planFormRelationList;

    @Column(name = "org_id")
    private Long orgId;

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
     * 获取任务计划名
     *
     * @return name - 任务计划名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置任务计划名
     *
     * @param name 任务计划名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取描述说明
     *
     * @return description - 描述说明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述说明
     *
     * @param description 描述说明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取所选择的部门ID
     *
     * @return dpt_id - 所选择的部门ID
     */
    public Long getDptId() {
        return dptId;
    }

    /**
     * 设置所选择的部门ID
     *
     * @param dptId 所选择的部门ID
     */
    public void setDptId(Long dptId) {
        this.dptId = dptId;
    }

    /**
     * 获取状态 0：启用 1：停用
     *
     * @return status - 状态 0：启用 1：停用
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态 0：启用 1：停用
     *
     * @param status 状态 0：启用 1：停用
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取计划开始月份
            1：月循环
            2：季度循环
            3：年循环
     *
     * @return plan_start_date - 计划开始月份
            1：月循环
            2：季度循环
            3：年循环
     */
    public Date getPlanStartDate() {
        return planStartDate;
    }

    /**
     * 设置计划开始月份
            1：月循环
            2：季度循环
            3：年循环
     *
     * @param planStartDate 计划开始月份
            1：月循环
            2：季度循环
            3：年循环
     */
    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    /**
     * 获取计划结束月份
     *
     * @return plan_end_date - 计划结束月份
     */
    public Date getPlanEndDate() {
        return planEndDate;
    }

    /**
     * 设置计划结束月份
     *
     * @param planEndDate 计划结束月份
     */
    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    /**
     * 获取巡店周期(月)
     *
     * @return plan_cycle - 巡店周期(月)
     */
    public Integer getPlanCycle() {
        return planCycle;
    }

    /**
     * 设置巡店周期(月)
     *
     * @param planCycle 巡店周期(月)
     */
    public void setPlanCycle(Integer planCycle) {
        this.planCycle = planCycle;
    }

    public List<TPlanFormRelation> getPlanFormRelationList() {
        return planFormRelationList;
    }

    public void setPlanFormRelationList(List<TPlanFormRelation> planFormRelationList) {
        this.planFormRelationList = planFormRelationList;
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
}