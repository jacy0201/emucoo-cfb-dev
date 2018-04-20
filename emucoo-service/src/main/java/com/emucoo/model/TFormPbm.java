package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_pbm")
public class TFormPbm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 题项名
     */
    private String name;

    /**
     * 分值
     */
    private Integer score;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "modify_user_id")
    private Long modifyUserId;

    /**
     * 子题项抽样目标数量
     */
    @Column(name = "sub_problem_num")
    private Integer subProblemNum;

    /**
     * 子题抽样目标单位
     */
    @Column(name = "sub_problem_unit")
    private String subProblemUnit;

    /**
     * 题项方案类型（1：不带抽样，2：带抽样）
     */
    @Column(name = "problem_schema_type")
    private Integer problemSchemaType;

    /**
     * 是否重要（0：不重要，1：重要）
     */
    @Column(name = "is_important")
    private Boolean isImportant;

    /**
     * 表单分类id
     */
    @Column(name = "form_type_id")
    private Long formTypeId;

    /**
     * 描述提示
     */
    @Column(name = "description_hit")
    private String descriptionHit;

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
     * 获取题项名
     *
     * @return name - 题项名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置题项名
     *
     * @param name 题项名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取分值
     *
     * @return score - 分值
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置分值
     *
     * @param score 分值
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * @return create_user_id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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
     * @return modify_user_id
     */
    public Long getModifyUserId() {
        return modifyUserId;
    }

    /**
     * @param modifyUserId
     */
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    /**
     * 获取子题项抽样目标数量
     *
     * @return sub_problem_num - 子题项抽样目标数量
     */
    public Integer getSubProblemNum() {
        return subProblemNum;
    }

    /**
     * 设置子题项抽样目标数量
     *
     * @param subProblemNum 子题项抽样目标数量
     */
    public void setSubProblemNum(Integer subProblemNum) {
        this.subProblemNum = subProblemNum;
    }

    /**
     * 获取子题抽样目标单位
     *
     * @return sub_problem_unit - 子题抽样目标单位
     */
    public String getSubProblemUnit() {
        return subProblemUnit;
    }

    /**
     * 设置子题抽样目标单位
     *
     * @param subProblemUnit 子题抽样目标单位
     */
    public void setSubProblemUnit(String subProblemUnit) {
        this.subProblemUnit = subProblemUnit;
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
     * 获取是否重要（0：不重要，1：重要）
     *
     * @return is_important - 是否重要（0：不重要，1：重要）
     */
    public Boolean getIsImportant() {
        return isImportant;
    }

    /**
     * 设置是否重要（0：不重要，1：重要）
     *
     * @param isImportant 是否重要（0：不重要，1：重要）
     */
    public void setIsImportant(Boolean isImportant) {
        this.isImportant = isImportant;
    }

    /**
     * 获取表单分类id
     *
     * @return form_type_id - 表单分类id
     */
    public Long getFormTypeId() {
        return formTypeId;
    }

    /**
     * 设置表单分类id
     *
     * @param formTypeId 表单分类id
     */
    public void setFormTypeId(Long formTypeId) {
        this.formTypeId = formTypeId;
    }

    /**
     * 获取描述提示
     *
     * @return description_hit - 描述提示
     */
    public String getDescriptionHit() {
        return descriptionHit;
    }

    /**
     * 设置描述提示
     *
     * @param descriptionHit 描述提示
     */
    public void setDescriptionHit(String descriptionHit) {
        this.descriptionHit = descriptionHit;
    }
}