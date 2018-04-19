package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "sys_dept")
public class SysDept extends BaseEntity {
    /**
     * 机构ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父ID ，层级关系
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 机构名称(公司或部门)
     */
    @Column(name = "dpt_name")
    private String dptName;

    /**
     * 机构编码
     */
    @Column(name = "dpt_no")
    private String dptNo;


    /**
     * 机构简称
     *
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 省份
     */
    private String province;


    /**
     *分区集合
     */
    @Transient
    private List<SysArea> areaList;


    /**

     *品牌集合
     */
    @Transient
    private List<TBrandInfo> brandList;


    /**

     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;


    /**
     * 详细地址
     */
    @Column(name = "address_detail")
    private String addressDetail;

    /**
     * 备注
     */
    private String remark;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String tel;

    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 描述
     */
    private String description;

    /**
     * 是启用 0：启用 1：不启用
     */
    @Column(name = "is_use")
    private Boolean isUse;

    //上级部门名称
    @Transient
    private String parentName;



    /**
     * ztree属性
     */
    @Transient
    private Boolean open;
    @Transient
    private List<?> list;


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

    /**
     * 获取机构ID
     *
     * @return id - 机构ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置机构ID
     *
     * @param id 机构ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取父ID ，层级关系
     *
     * @return parent_id - 父ID ，层级关系
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父ID ，层级关系
     *
     * @param parentId 父ID ，层级关系
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取机构名称(公司或部门)
     *
     * @return dpt_name - 机构名称(公司或部门)
     */
    public String getDptName() {
        return dptName;
    }

    /**
     * 设置机构名称(公司或部门)
     *
     * @param dptName 机构名称(公司或部门)
     */
    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    /**
     * 获取机构编码
     *
     * @return dpt_no - 机构编码
     */
    public String getDptNo() {
        return dptNo;
    }

    /**
     * 设置机构编码
     *
     * @param dptNo 机构编码
     */
    public void setDptNo(String dptNo) {
        this.dptNo = dptNo;
    }


    /**
     * 获取详细地址
     *
     * @return address_detail - 详细地址
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * 设置详细地址
     *
     * @param addressDetail 详细地址
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取负责人
     *
     * @return leader - 负责人
     */
    public String getLeader() {
        return leader;
    }

    /**
     * 设置负责人
     *
     * @param leader 负责人
     */
    public void setLeader(String leader) {
        this.leader = leader;
    }

    /**
     * 获取联系电话
     *
     * @return tel - 联系电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置联系电话
     *
     * @param tel 联系电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return order_num
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取是启用 0：启用 1：不启用
     *
     * @return is_use - 是启用 0：启用 1：不启用
     */
    public Boolean getIsUse() {
        return isUse;
    }

    /**
     * 设置是启用 0：启用 1：不启用
     *
     * @param isUse 是启用 0：启用 1：不启用
     */
    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
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


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public List<SysArea> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<SysArea> areaList) {
        this.areaList = areaList;
    }

    public List<TBrandInfo> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<TBrandInfo> brandList) {
        this.brandList = brandList;
    }
}