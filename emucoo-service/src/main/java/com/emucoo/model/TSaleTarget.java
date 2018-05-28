package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import javax.persistence.*;

@Table(name = "t_sale_target")
public class TSaleTarget extends BaseEntity {
    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 工作目标id  , 关联 t_work_target
     */
    @Column(name = "work_target_id")
    private Long workTargetId;

    public Long getWorkTargetId() {
        return workTargetId;
    }

    public void setWorkTargetId(Long workTargetId) {
        this.workTargetId = workTargetId;
    }

    /**
     * 店铺id
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 目标金额
     */
    @Column(name = "target_amount")
    private Double targetAmount;

    /**
     * 实际金额
     */
    @Column(name = "actual_amount")
    private Double actualAmount;

    /**
     * 获取PK
     *
     * @return id - PK
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置PK
     *
     * @param id PK
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取店铺id
     *
     * @return shop_id - 店铺id
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * 设置店铺id
     *
     * @param shopId 店铺id
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取目标金额
     *
     * @return target_amount - 目标金额
     */
    public Double getTargetAmount() {
        return targetAmount;
    }

    /**
     * 设置目标金额
     *
     * @param targetAmount 目标金额
     */
    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * 获取实际金额
     *
     * @return actual_amount - 实际金额
     */
    public Double getActualAmount() {
        return actualAmount;
    }

    /**
     * 设置实际金额
     *
     * @param actualAmount 实际金额
     */
    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }
}