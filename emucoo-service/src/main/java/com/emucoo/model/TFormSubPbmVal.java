package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

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
}