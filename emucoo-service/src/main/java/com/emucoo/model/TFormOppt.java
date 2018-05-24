package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_oppt")
public class TFormOppt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 题项表id
     */
    @Column(name = "problem_id")
    private Long problemId;

    @Transient
    private String opptName;

    /**
     * 机会点id
     */
    @Column(name = "oppt_id")
    private Long opptId;

    @Transient
    private String opptIdStr;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 题项方案类型（1：不带抽样，2：带抽样）
     */
    @Column(name = "problem_type")
    private Integer problemType;

    @Column(name = "sub_problem_id")
    private Long subProblemId;

    @Transient
    private TOpportunity opportunity;

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
     * 获取题项表id
     *
     * @return problem_id - 题项表id
     */
    public Long getProblemId() {
        return problemId;
    }

    /**
     * 设置题项表id
     *
     * @param problemId 题项表id
     */
    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    /**
     * 获取机会点id
     *
     * @return oppt_id - 机会点id
     */
    public Long getOpptId() {
        return opptId;
    }

    /**
     * 设置机会点id
     *
     * @param opptId 机会点id
     */
    public void setOpptId(Long opptId) {
        this.opptId = opptId;
    }

    public String getOpptIdStr() {
        return opptIdStr;
    }

    public void setOpptIdStr(String opptIdStr) {
        this.opptIdStr = opptIdStr;
    }

    public String getOpptName() {
        return opptName;
    }

    public void setOpptName(String opptName) {
        this.opptName = opptName;
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
     * 获取题项方案类型（1：不带抽样，2：带抽样）
     *
     * @return problem_type - 题项方案类型（1：不带抽样，2：带抽样）
     */
    public Integer getProblemType() {
        return problemType;
    }

    /**
     * 设置题项方案类型（1：不带抽样，2：带抽样）
     *
     * @param problemType 题项方案类型（1：不带抽样，2：带抽样）
     */
    public void setProblemType(Integer problemType) {
        this.problemType = problemType;
    }

    /**
     * @return sub_problem_id
     */
    public Long getSubProblemId() {
        return subProblemId;
    }

    /**
     * @param subProblemId
     */
    public void setSubProblemId(Long subProblemId) {
        this.subProblemId = subProblemId;
    }

    public TOpportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(TOpportunity opportunity) {
        this.opportunity = opportunity;
    }
}