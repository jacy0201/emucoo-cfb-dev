package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_form_impt_rules")
public class TFormImptRules extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 统计出的重点项数量
     */
    @Column(name = "count_num")
    private Integer countNum;

    /**
     * 分数折扣率
     */
    @Column(name = "discount_num")
    private Integer discountNum;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 表单主id
     */
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
     * 获取统计出的重点项数量
     *
     * @return count_num - 统计出的重点项数量
     */
    public Integer getCountNum() {
        return countNum;
    }

    /**
     * 设置统计出的重点项数量
     *
     * @param countNum 统计出的重点项数量
     */
    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    /**
     * 获取分数折扣率
     *
     * @return discount_num - 分数折扣率
     */
    public Integer getDiscountNum() {
        return discountNum;
    }

    /**
     * 设置分数折扣率
     *
     * @param discountNum 分数折扣率
     */
    public void setDiscountNum(Integer discountNum) {
        this.discountNum = discountNum;
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
     * 获取表单主id
     *
     * @return form_main_id - 表单主id
     */
    public Long getFormMainId() {
        return formMainId;
    }

    /**
     * 设置表单主id
     *
     * @param formMainId 表单主id
     */
    public void setFormMainId(Long formMainId) {
        this.formMainId = formMainId;
    }
}