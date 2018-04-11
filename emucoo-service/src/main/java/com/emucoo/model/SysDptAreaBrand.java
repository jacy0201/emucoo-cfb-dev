package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_dpt_area_brand")
public class SysDptAreaBrand extends BaseEntity {
    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 机构表ID
     */
    @Column(name = "dpt_id")
    private Long dptId;

    /**
     * sys_area表 主键
     */
    @Column(name = "area_id")
    private Long areaId;

    /**
     * brand_info 表主键
     */
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 1-已删除；0-未删除
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
     * 获取机构表ID
     *
     * @return dpt_id - 机构表ID
     */
    public Long getDptId() {
        return dptId;
    }

    /**
     * 设置机构表ID
     *
     * @param dptId 机构表ID
     */
    public void setDptId(Long dptId) {
        this.dptId = dptId;
    }

    /**
     * 获取sys_area表 主键
     *
     * @return area_id - sys_area表 主键
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * 设置sys_area表 主键
     *
     * @param areaId sys_area表 主键
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取brand_info 表主键
     *
     * @return brand_id - brand_info 表主键
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * 设置brand_info 表主键
     *
     * @param brandId brand_info 表主键
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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
     * 获取1-已删除；0-未删除
     *
     * @return is_del - 1-已删除；0-未删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置1-已删除；0-未删除
     *
     * @param isDel 1-已删除；0-未删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}