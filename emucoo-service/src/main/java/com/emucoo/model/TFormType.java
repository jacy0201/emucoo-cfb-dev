package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_type")
public class TFormType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 类型名
     */
    @Column(name = "type_name")
    private String typeName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "form_main_id")
    private Long formMainId;

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
     * 获取类型名
     *
     * @return type_name - 类型名
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置类型名
     *
     * @param typeName 类型名
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
     * @return form_main_id
     */
    public Long getFormMainId() {
        return formMainId;
    }

    /**
     * @param formMainId
     */
    public void setFormMainId(Long formMainId) {
        this.formMainId = formMainId;
    }
}