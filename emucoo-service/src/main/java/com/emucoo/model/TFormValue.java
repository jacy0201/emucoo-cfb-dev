package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_value")
public class TFormValue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联表单主表
     */
    @Column(name = "form_main_id")
    private Long formMainId;

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
     * 获取关联表单主表
     *
     * @return form_main_id - 关联表单主表
     */
    public Long getFormMainId() {
        return formMainId;
    }

    /**
     * 设置关联表单主表
     *
     * @param formMainId 关联表单主表
     */
    public void setFormMainId(Long formMainId) {
        this.formMainId = formMainId;
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
}