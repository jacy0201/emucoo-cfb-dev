package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import javax.persistence.*;

@Table(name = "sys_area")
public class SysArea extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 分区描述
     */
    @Column(name = "area_desc")
    private String areaDesc;

    /**
     * 0-未删除；1-已删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取分区名称
     *
     * @return area_name - 分区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置分区名称
     *
     * @param areaName 分区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取分区描述
     *
     * @return area_desc - 分区描述
     */
    public String getAreaDesc() {
        return areaDesc;
    }

    /**
     * 设置分区描述
     *
     * @param areaDesc 分区描述
     */
    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
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