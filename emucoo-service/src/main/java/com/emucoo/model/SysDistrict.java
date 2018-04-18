package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_district")
public class SysDistrict extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 区域编码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 父级编号
     */
    @Column(name = "parent_code")
    private String parentCode;

    /**
     * 所有父级编号
     */
    @Column(name = "parent_codes")
    private String parentCodes;

    /**
     * 本级排序号（升序）
     */
    @Column(name = "tree_sort")
    private Long treeSort;

    /**
     * 所有级别排序号
     */
    @Column(name = "tree_sorts")
    private String treeSorts;

    /**
     * 是否最末级
     */
    @Column(name = "tree_leaf")
    private String treeLeaf;

    /**
     * 层次级别
     */
    @Column(name = "tree_level")
    private Short treeLevel;

    /**
     * 全节点名
     */
    @Column(name = "tree_names")
    private String treeNames;

    /**
     * 区域名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 区域类型
     */
    @Column(name = "area_type")
    private String areaType;

    /**
     * 状态（0正常 1删除 2停用）
     */
    private String status;

    /**
     * 创建者
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "modify_user_id")
    private Long modifyUserId;

    /**
     * 更新时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 备注信息
     */
    private String remarks;

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
     * 获取区域编码
     *
     * @return area_code - 区域编码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置区域编码
     *
     * @param areaCode 区域编码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取父级编号
     *
     * @return parent_code - 父级编号
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 设置父级编号
     *
     * @param parentCode 父级编号
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * 获取所有父级编号
     *
     * @return parent_codes - 所有父级编号
     */
    public String getParentCodes() {
        return parentCodes;
    }

    /**
     * 设置所有父级编号
     *
     * @param parentCodes 所有父级编号
     */
    public void setParentCodes(String parentCodes) {
        this.parentCodes = parentCodes;
    }

    /**
     * 获取本级排序号（升序）
     *
     * @return tree_sort - 本级排序号（升序）
     */
    public Long getTreeSort() {
        return treeSort;
    }

    /**
     * 设置本级排序号（升序）
     *
     * @param treeSort 本级排序号（升序）
     */
    public void setTreeSort(Long treeSort) {
        this.treeSort = treeSort;
    }

    /**
     * 获取所有级别排序号
     *
     * @return tree_sorts - 所有级别排序号
     */
    public String getTreeSorts() {
        return treeSorts;
    }

    /**
     * 设置所有级别排序号
     *
     * @param treeSorts 所有级别排序号
     */
    public void setTreeSorts(String treeSorts) {
        this.treeSorts = treeSorts;
    }

    /**
     * 获取是否最末级
     *
     * @return tree_leaf - 是否最末级
     */
    public String getTreeLeaf() {
        return treeLeaf;
    }

    /**
     * 设置是否最末级
     *
     * @param treeLeaf 是否最末级
     */
    public void setTreeLeaf(String treeLeaf) {
        this.treeLeaf = treeLeaf;
    }

    /**
     * 获取层次级别
     *
     * @return tree_level - 层次级别
     */
    public Short getTreeLevel() {
        return treeLevel;
    }

    /**
     * 设置层次级别
     *
     * @param treeLevel 层次级别
     */
    public void setTreeLevel(Short treeLevel) {
        this.treeLevel = treeLevel;
    }

    /**
     * 获取全节点名
     *
     * @return tree_names - 全节点名
     */
    public String getTreeNames() {
        return treeNames;
    }

    /**
     * 设置全节点名
     *
     * @param treeNames 全节点名
     */
    public void setTreeNames(String treeNames) {
        this.treeNames = treeNames;
    }

    /**
     * 获取区域名称
     *
     * @return area_name - 区域名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置区域名称
     *
     * @param areaName 区域名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取区域类型
     *
     * @return area_type - 区域类型
     */
    public String getAreaType() {
        return areaType;
    }

    /**
     * 设置区域类型
     *
     * @param areaType 区域类型
     */
    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    /**
     * 获取状态（0正常 1删除 2停用）
     *
     * @return status - 状态（0正常 1删除 2停用）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态（0正常 1删除 2停用）
     *
     * @param status 状态（0正常 1删除 2停用）
     */
    public void setStatus(String status) {
        this.status = status;
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
     * 获取备注信息
     *
     * @return remarks - 备注信息
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注信息
     *
     * @param remarks 备注信息
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}