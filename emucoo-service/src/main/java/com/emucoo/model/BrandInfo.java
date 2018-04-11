package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import javax.persistence.*;

@Table(name = "brand_info")
public class BrandInfo extends BaseEntity {
    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 品牌编码
     */
    @Column(name = "brand_code")
    private String brandCode;

    /**
     * 品牌名称
     */
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 0-未删除；1-已删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

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
     * 获取品牌编码
     *
     * @return brand_code - 品牌编码
     */
    public String getBrandCode() {
        return brandCode;
    }

    /**
     * 设置品牌编码
     *
     * @param brandCode 品牌编码
     */
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    /**
     * 获取品牌名称
     *
     * @return brand_name - 品牌名称
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置品牌名称
     *
     * @param brandName 品牌名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取0-未删除；1-已删除
     *
     * @return is_del - 0-未删除；1-已删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置0-未删除；1-已删除
     *
     * @param isDel 0-未删除；1-已删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}