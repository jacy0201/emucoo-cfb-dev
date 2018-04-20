package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_sub_pbm")
public class TFormSubPbm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 子题项名
     */
    @Column(name = "sub_problem_name")
    private String subProblemName;

    /**
     * 子题项分数
     */
    @Column(name = "sub_problem_score")
    private Integer subProblemScore;

    /**
     * 题项id
     */
    @Column(name = "form_problem_id")
    private Long formProblemId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "modify_user_id")
    private Long modifyUserId;

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
     * 获取子题项名
     *
     * @return sub_problem_name - 子题项名
     */
    public String getSubProblemName() {
        return subProblemName;
    }

    /**
     * 设置子题项名
     *
     * @param subProblemName 子题项名
     */
    public void setSubProblemName(String subProblemName) {
        this.subProblemName = subProblemName;
    }

    /**
     * 获取子题项分数
     *
     * @return sub_problem_score - 子题项分数
     */
    public Integer getSubProblemScore() {
        return subProblemScore;
    }

    /**
     * 设置子题项分数
     *
     * @param subProblemScore 子题项分数
     */
    public void setSubProblemScore(Integer subProblemScore) {
        this.subProblemScore = subProblemScore;
    }

    /**
     * 获取题项id
     *
     * @return form_problem_id - 题项id
     */
    public Long getFormProblemId() {
        return formProblemId;
    }

    /**
     * 设置题项id
     *
     * @param formProblemId 题项id
     */
    public void setFormProblemId(Long formProblemId) {
        this.formProblemId = formProblemId;
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