package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_form_sub_pbm_val")
public class TFormSubPbmVal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_problem_id")
    private Long subProblemId;

    /**
     * 子题项实际得分
     */
    @Column(name = "sub_problem_score")
    private Integer subProblemScore;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "problem_value_id")
    private Long problemValueId;

    @Column(name = "sub_problem_name")
    private String subProblemName;

    @Column(name = "form_result_id")
    private Long formResultId;

    @Column(name = "sub_form_id")
    private Long subFormId;

    @Column(name = "is_pass")
    private Boolean isPass;

    @Column(name = "is_score")
    private Boolean isScore;

    @Column(name = "is_na")
    private Boolean isNa;

    @Column(name = "problem_description")
    private String problemDescription;

    @Column(name = "check_method")
    private String checkMethod;

    private String notes;

    @Column(name = "desc_img_ids")
    private String descImgIds;

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
     * 获取子题项实际得分
     *
     * @return sub_problem_score - 子题项实际得分
     */
    public Integer getSubProblemScore() {
        return subProblemScore;
    }

    /**
     * 设置子题项实际得分
     *
     * @param subProblemScore 子题项实际得分
     */
    public void setSubProblemScore(Integer subProblemScore) {
        this.subProblemScore = subProblemScore;
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
     * @return sub_problem_name
     */
    public String getSubProblemName() {
        return subProblemName;
    }

    /**
     * @param subProblemName
     */
    public void setSubProblemName(String subProblemName) {
        this.subProblemName = subProblemName;
    }

    public Boolean getIsNa() {
        return isNa;
    }

    public void setIsNa(Boolean isNa) {
        this.isNa = isNa;
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

    public Long getFormResultId() {
        return formResultId;
    }

    public void setFormResultId(Long formResultId) {
        this.formResultId = formResultId;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }

    public Boolean getIsScore() {
        return isScore;
    }

    public void setIsScore(Boolean isScore) {
        this.isScore = isScore;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getSubFormId() {
        return subFormId;
    }

    public void setSubFormId(Long subFormId) {
        this.subFormId = subFormId;
    }

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }

    public String getDescImgIds() {
        return descImgIds;
    }

    public void setDescImgIds(String descImgIds) {
        this.descImgIds = descImgIds;
    }
}