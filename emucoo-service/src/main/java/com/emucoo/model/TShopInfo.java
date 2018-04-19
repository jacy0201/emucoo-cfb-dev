package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_shop_info")
@ApiModel
public class TShopInfo extends BaseEntity {
    /**
     * 主键PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 店铺名称
     */
    @Column(name = "shop_name")
    private String shopName;

    /**
     * 店铺简称
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 店铺编号
     */
    @Column(name = "shop_code")
    private String shopCode;

    /**
     * 店铺描述
     */
    @Column(name = "shop_desc")
    private String shopDesc;


    /**
     * 是否启用：1-启用；0-停用
     */
    @Column(name = "is_use")
    @ApiModelProperty(name = "isUse", value = "状态 false：停用 true：启用",example = "true")
    private  Boolean isUse;

    public Boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }


    /**
     * 省份
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 店铺地址
     */
    private String address;

    /**
     * 店长
     */
    @Column(name = "shop_manager_id")
    private Long shopManagerId;

    /**
     * 分区id,关联sys_area 表id
     */
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 分区名称
     */
    @Transient
    private String areaName;

    /**
     * 品牌id, 关联brand_info 表主键
     */
    @Column(name = "brand_id")
    private Long brandId;
    /**

     * 品牌名称
     */
    @Transient
    private String brandName;


    /**
     * 店铺电话
     */
    private String tel;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 数据创建人ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 数据修改人ID
     */
    @Column(name = "modify_user_id")
    private Long modifyUserId;

    /**
     * 是否逻辑删除0：正常1：逻辑删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

    @Column(name = "org_id")
    private Long orgId;

    /**
     * 获取主键PK
     *
     * @return id - 主键PK
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键PK
     *
     * @param id 主键PK
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取店铺名称
     *
     * @return shop_name - 店铺名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置店铺名称
     *
     * @param shopName 店铺名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 获取店铺简称
     *
     * @return short_name - 店铺简称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置店铺简称
     *
     * @param shortName 店铺简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 获取店铺编号
     *
     * @return shop_code - 店铺编号
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * 设置店铺编号
     *
     * @param shopCode 店铺编号
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    /**
     * 获取店铺描述
     *
     * @return shop_desc - 店铺描述
     */
    public String getShopDesc() {
        return shopDesc;
    }

    /**
     * 设置店铺描述
     *
     * @param shopDesc 店铺描述
     */
    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取省份
     *
     * @return province - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取区
     *
     * @return district - 区
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置区
     *
     * @param district 区
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 获取店铺地址
     *
     * @return address - 店铺地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置店铺地址
     *
     * @param address 店铺地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取店长
     *
     * @return shop_manager_id - 店长
     */
    public Long getShopManagerId() {
        return shopManagerId;
    }

    /**
     * 设置店长
     *
     * @param shopManagerId 店长
     */
    public void setShopManagerId(Long shopManagerId) {
        this.shopManagerId = shopManagerId;
    }

    /**
     * 获取分区id,关联sys_area 表id
     *
     * @return area_id - 分区id,关联sys_area 表id
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * 设置分区id,关联sys_area 表id
     *
     * @param areaId 分区id,关联sys_area 表id
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取品牌id, 关联brand_info 表主键
     *
     * @return brand_id - 品牌id, 关联brand_info 表主键
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * 设置品牌id, 关联brand_info 表主键
     *
     * @param brandId 品牌id, 关联brand_info 表主键
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取店铺电话
     *
     * @return tel - 店铺电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置店铺电话
     *
     * @param tel 店铺电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取数据创建人ID
     *
     * @return create_user_id - 数据创建人ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置数据创建人ID
     *
     * @param createUserId 数据创建人ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取数据修改人ID
     *
     * @return modify_user_id - 数据修改人ID
     */
    public Long getModifyUserId() {
        return modifyUserId;
    }

    /**
     * 设置数据修改人ID
     *
     * @param modifyUserId 数据修改人ID
     */
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    /**
     * 获取是否逻辑删除0：正常1：逻辑删除
     *
     * @return is_del - 是否逻辑删除0：正常1：逻辑删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置是否逻辑删除0：正常1：逻辑删除
     *
     * @param isDel 是否逻辑删除0：正常1：逻辑删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    /**
     * @return org_id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}