package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_form_pbm_val")
public class TFormPbmVal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联表单结果表id
     */
    @Column(name = "form_value_id")
    private Long formValueId;

    /**
     * 表单题项表id
     */
    @Column(name = "form_problem_id")
    private Long formProblemId;

    /**
     * 实际分数
     */
    private Integer score;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 是否是NA
     */
    @Column(name = "is_na")
    private Boolean isNa;

    /**
     * 该题项是否经过实际打分动作
     */
    @Column(name = "is_score")
    private Boolean isScore;

    @Column(name = "is_important")
    private Boolean isImportant;

    @Column(name = "org_id")
    private Long orgId;

    /**
     * 题项方案类型（1：不带抽样，2：带抽样）
     */
    @Column(name = "problem_schema_type")
    private Integer problemSchemaType;

    @Column(name = "problem_name")
    private String problemName;

    /**
     * 题项描述
     */
    @Column(name = "problem_description")
    private String problemDescription;

    @Column(name = "form_result_id")
    private Long formResultId;

    private String notes;

    @Column(name = "is_pass")
    private Boolean isPass;

    @Column(name = "check_method")
    private String checkMethod;

    @Column(name = "sub_form_id")
    private Long subFormId;

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
     * 获取关联表单结果表id
     *
     * @return form_value_id - 关联表单结果表id
     */
    public Long getFormValueId() {
        return formValueId;
    }

    /**
     * 设置关联表单结果表id
     *
     * @param formValueId 关联表单结果表id
     */
    public void setFormValueId(Long formValueId) {
        this.formValueId = formValueId;
    }

    /**
     * 获取表单题项表id
     *
     * @return form_problem_id - 表单题项表id
     */
    public Long getFormProblemId() {
        return formProblemId;
    }

    /**
     * 设置表单题项表id
     *
     * @param formProblemId 表单题项表id
     */
    public void setFormProblemId(Long formProblemId) {
        this.formProblemId = formProblemId;
    }

    /**
     * 获取实际分数
     *
     * @return score - 实际分数
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置实际分数
     *
     * @param score 实际分数
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Boolean isImportant) {
        this.isImportant = isImportant;
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
     * 获取是否是NA
     *
     * @return is_na - 是否是NA
     */
    public Boolean getIsNa() {
        return isNa;
    }

    /**
     * 设置是否是NA
     *
     * @param isNa 是否是NA
     */
    public void setIsNa(Boolean isNa) {
        this.isNa = isNa;
    }

    /**
     * 获取该题项是否经过实际打分动作
     *
     * @return is_score - 该题项是否经过实际打分动作
     */
    public Boolean getIsScore() {
        return isScore;
    }

    /**
     * 设置该题项是否经过实际打分动作
     *
     * @param isScore 该题项是否经过实际打分动作
     */
    public void setIsScore(Boolean isScore) {
        this.isScore = isScore;
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

    /**
     * 获取题项方案类型（1：不带抽样，2：带抽样）
     *
     * @return problem_schema_type - 题项方案类型（1：不带抽样，2：带抽样）
     */
    public Integer getProblemSchemaType() {
        return problemSchemaType;
    }

    /**
     * 设置题项方案类型（1：不带抽样，2：带抽样）
     *
     * @param problemSchemaType 题项方案类型（1：不带抽样，2：带抽样）
     */
    public void setProblemSchemaType(Integer problemSchemaType) {
        this.problemSchemaType = problemSchemaType;
    }

    /**
     * @return problem_name
     */
    public String getProblemName() {
        return problemName;
    }

    /**
     * @param problemName
     */
        public void setProblemName (String problemName){
            this.problemName = problemName;
        }

        /**
         * 获取题项描述
         *
         * @return problem_description - 题项描述
         */

    public String getProblemDescription() {
        return problemDescription;
    }

    /**
     * 设置题项描述
     *
     * @param problemDescription 题项描述
     */
    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public Long getFormResultId() {
        return formResultId;
    }

    public void setFormResultId(Long formResultId) {
        this.formResultId = formResultId;
    }

    public Long getSubFormId() {
        return subFormId;
    }

    public void setSubFormId(Long subFormId) {
        this.subFormId = subFormId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }
}