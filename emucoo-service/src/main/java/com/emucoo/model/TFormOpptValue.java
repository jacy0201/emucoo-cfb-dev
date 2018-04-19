package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_oppt_value")
public class TFormOpptValue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form_oppt_id")
    private Long formOpptId;

    /**
     * 该机会点是否已经点选
     */
    @Column(name = "is_pick")
    private Boolean isPick;

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
     * @return form_oppt_id
     */
    public Long getFormOpptId() {
        return formOpptId;
    }

    /**
     * @param formOpptId
     */
    public void setFormOpptId(Long formOpptId) {
        this.formOpptId = formOpptId;
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