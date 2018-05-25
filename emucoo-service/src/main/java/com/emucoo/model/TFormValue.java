package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_form_value")
public class TFormValue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 表单分类id
     */
    @Column(name = "from_type_id")
    private Long fromTypeId;

    /**
     * 是否完成
     */
    @Column(name = "is_done")
    private Boolean isDone;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "form_type_name")
    private String formTypeName;

    @Column(name = "total")
    private Integer total;

    /**
     * l类型得分率
     */
    @Column(name = "score_rate")
    private Float scoreRate;

    @Column(name = "score")
    private Integer score;

    @Column(name = "form_result_id")
    private Long formResultId;

    @Column(name = "is_pass")
    private Boolean isPass;

    @Transient
    private List<TFormPbmVal> formPbmValList;

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
     * 获取表单分类id
     *
     * @return from_type_id - 表单分类id
     */
    public Long getFromTypeId() {
        return fromTypeId;
    }

    /**
     * 设置表单分类id
     *
     * @param fromTypeId 表单分类id
     */
    public void setFromTypeId(Long fromTypeId) {
        this.fromTypeId = fromTypeId;
    }

    /**
     * 获取是否完成
     *
     * @return is_done - 是否完成
     */
    public Boolean getIsDone() {
        return isDone;
    }

    /**
     * 设置是否完成
     *
     * @param isDone 是否完成
     */
    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
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
     * @return form_type_name
     */
    public String getFormTypeName() {
        return formTypeName;
    }

    /**
     * @param formTypeName
     */
    public void setFormTypeName(String formTypeName) {
        this.formTypeName = formTypeName;
    }

    /**
     * 获取l类型得分率
     *
     * @return score_rate - l类型得分率
     */
    public Float getScoreRate() {
        return scoreRate;
    }

    /**
     * 设置l类型得分率
     *
     * @param scoreRate l类型得分率
     */
    public void setScoreRate(Float scoreRate) {
        this.scoreRate = scoreRate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getFormResultId() {
        return formResultId;
    }

    public void setFormResultId(Long formResultId) {
        this.formResultId = formResultId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }

    public List<TFormPbmVal> getFormPbmValList() {
        return formPbmValList;
    }

    public void setFormPbmValList(List<TFormPbmVal> formPbmValList) {
        this.formPbmValList = formPbmValList;
    }
}