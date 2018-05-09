package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_loop_sub_plan")
public class TLoopSubPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 计划周期开始年月份
     */
    @Column(name = "cycle_begin")
    private String cycleBegin;

    /**
     * 计划周期结束年月份
     */
    @Column(name = "cycle_end")
    private String cycleEnd;

    /**
     * 计划id
     */
    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "cur_cycle_count")
    private Integer curCycleCount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "dpt_id")
    private Long dptId;

    private Boolean isDel;

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

    public String getCycleBegin() {
        return cycleBegin;
    }

    public void setCycleBegin(String cycleBegin) {
        this.cycleBegin = cycleBegin;
    }

    public String getCycleEnd() {
        return cycleEnd;
    }

    public void setCycleEnd(String cycleEnd) {
        this.cycleEnd = cycleEnd;
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

    public Integer getCurCycleCount() {
        return curCycleCount;
    }

    public void setCurCycleCount(Integer curCycleCount) {
        this.curCycleCount = curCycleCount;
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

    /**
     * @return dpt_id
     */
    public Long getDptId() {
        return dptId;
    }

    /**
     * @param dptId
     */
    public void setDptId(Long dptId) {
        this.dptId = dptId;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}