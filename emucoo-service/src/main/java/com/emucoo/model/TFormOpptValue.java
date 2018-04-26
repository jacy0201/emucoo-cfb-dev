package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_oppt_value")
public class TFormOpptValue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 该机会点是否已经点选
     */
    @Column(name = "is_pick")
    private Boolean isPick;

    /**
     * 子题项单元分数
     */
    @Column(name = "sub_problem_unit_score")
    private Integer subProblemUnitScore;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "oppt_id")
    private Long opptId;

    @Column(name = "problem_id")
    private Long problemId;

    @Column(name = "sub_problem_id")
    private Long subProblemId;

    @Column(name = "oppt_name")
    private String opptName;

    @Column(name = "problem_value_id")
    private Long problemValueId;

    @Column(name = "sub_problem_value_id")
    private Long subProblemValueId;

    @Column(name = "problem_type")
    private Byte problemType;

    @Column(name = "sub_header_id")
    private Long subHeaderId;

    @Column(name = "form_result_id")
    private Long formResultId;

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
     * 获取该机会点是否已经点选
     *
     * @return is_pick - 该机会点是否已经点选
     */
    public Boolean getIsPick() {
        return isPick;
    }

    /**
     * 设置该机会点是否已经点选
     *
     * @param isPick 该机会点是否已经点选
     */
    public void setIsPick(Boolean isPick) {
        this.isPick = isPick;
    }

    /**
     * 获取子题项单元分数
     *
     * @return sub_problem_unit_score - 子题项单元分数
     */
    public Integer getSubProblemUnitScore() {
        return subProblemUnitScore;
    }

    /**
     * 设置子题项单元分数
     *
     * @param subProblemUnitScore 子题项单元分数
     */
    public void setSubProblemUnitScore(Integer subProblemUnitScore) {
        this.subProblemUnitScore = subProblemUnitScore;
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
     * @return oppt_id
     */
    public Long getOpptId() {
        return opptId;
    }

    /**
     * @param opptId
     */
    public void setOpptId(Long opptId) {
        this.opptId = opptId;
    }

    /**
     * @return problem_id
     */
    public Long getProblemId() {
        return problemId;
    }

    /**
     * @param problemId
     */
    public void setProblemId(Long problemId) {
        this.problemId = problemId;
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

    /**
     * @return oppt_name
     */
    public String getOpptName() {
        return opptName;
    }

    /**
     * @param opptName
     */
    public void setOpptName(String opptName) {
        this.opptName = opptName;
    }

    /**
     * @return problem_value_id
     */
    public Long getProblemValueId() {
        return problemValueId;
    }

    /**
     * @param problemValueId
     */
    public void setProblemValueId(Long problemValueId) {
        this.problemValueId = problemValueId;
    }

    /**
     * @return sub_problem_value_id
     */
    public Long getSubProblemValueId() {
        return subProblemValueId;
    }

    /**
     * @param subProblemValueId
     */
    public void setSubProblemValueId(Long subProblemValueId) {
        this.subProblemValueId = subProblemValueId;
    }

    public Byte getProblemType() {
        return problemType;
    }

    public void setProblemType(Byte problemType) {
        this.problemType = problemType;
    }

    /**
     * @return sub_header_id
     */
    public Long getSubHeaderId() {
        return subHeaderId;
    }

    /**
     * @param subHeaderId
     */
    public void setSubHeaderId(Long subHeaderId) {
        this.subHeaderId = subHeaderId;
    }

    public Long getFormResultId() {
        return formResultId;
    }

    public void setFormResultId(Long formResultId) {
        this.formResultId = formResultId;
    }
}
