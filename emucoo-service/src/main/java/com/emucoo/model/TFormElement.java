package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_element")
public class TFormElement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 元素名
     */
    private String name;

    /**
     * 元素类型
     */
    private String type;

    /**
     * 是否删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

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
     * 获取元素名
     *
     * @return name - 元素名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置元素名
     *
     * @param name 元素名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取元素类型
     *
     * @return type - 元素类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置元素类型
     *
     * @param type 元素类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取是否删除
     *
     * @return is_del - 是否删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除
     *
     * @param isDel 是否删除
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
}