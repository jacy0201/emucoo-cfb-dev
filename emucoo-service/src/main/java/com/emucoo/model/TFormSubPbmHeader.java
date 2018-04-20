package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_form_sub_pbm_header")
public class TFormSubPbmHeader extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "form_problem_id")
    private Long formProblemId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Transient
    private List<TFormSubPbm> subProblems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFormProblemId() {
        return formProblemId;
    }

    public void setFormProblemId(Long formProblemId) {
        this.formProblemId = formProblemId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<TFormSubPbm> getSubProblems() {
        return subProblems;
    }

    public void setSubProblems(List<TFormSubPbm> subProblems) {
        this.subProblems = subProblems;
    }
}
