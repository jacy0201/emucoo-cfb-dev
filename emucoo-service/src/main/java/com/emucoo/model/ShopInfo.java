package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "shop_info")
public class ShopInfo extends BaseEntity {
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
     * 品牌id, 关联brand_info 表主键
     */
    @Column(name = "brand_id")
    private Long brandId;

    /**
     * 店铺电话
     */
    private String tel;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 1-已删除；0-未删除
     */
    @Column(name = "is_del")
    private Boolean isDel;

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
     * @return create_user_id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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