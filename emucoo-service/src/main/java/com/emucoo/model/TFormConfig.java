package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_config")
public class TFormConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 表单主表id
     */
    @Column(name = "form_main_id")
    private Long formMainId;

    /**
     * 表单元素id
     */
    @Column(name = "form_element_id")
    private Long formElementId;

    /**
     * 表单父配置id
     */
    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "is_del")
    private Boolean isDel;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 表单元素值
     */
    @Column(name = "form_element_value")
    private String formElementValue;

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
     * 获取表单主表id
     *
     * @return form_main_id - 表单主表id
     */
    public Long getFormMainId() {
        return formMainId;
    }

    /**
     * 设置表单主表id
     *
     * @param formMainId 表单主表id
     */
    public void setFormMainId(Long formMainId) {
        this.formMainId = formMainId;
    }

    /**
     * 获取表单元素id
     *
     * @return form_element_id - 表单元素id
     */
    public Long getFormElementId() {
        return formElementId;
    }

    /**
     * 设置表单元素id
     *
     * @param formElementId 表单元素id
     */
    public void setFormElementId(Long formElementId) {
        this.formElementId = formElementId;
    }

    /**
     * 获取表单父配置id
     *
     * @return parent_id - 表单父配置id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置表单父配置id
     *
     * @param parentId 表单父配置id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return is_del
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * @param isDel
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
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
     * 获取表单元素值
     *
     * @return form_element_value - 表单元素值
     */
    public String getFormElementValue() {
        return formElementValue;
    }

    /**
     * 设置表单元素值
     *
     * @param formElementValue 表单元素值
     */
    public void setFormElementValue(String formElementValue) {
        this.formElementValue = formElementValue;
    }
}