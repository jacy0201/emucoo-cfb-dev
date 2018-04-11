package com.emucoo.model;

import com.emucoo.common.base.model.BaseEntity;
import javax.persistence.*;

@Table(name = "sys_role_menu")
public class SysRoleMenu extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 资源表ID, sys_menu 表主键
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 1-已删除；0-未删除
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
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取资源表ID, sys_menu 表主键
     *
     * @return menu_id - 资源表ID, sys_menu 表主键
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置资源表ID, sys_menu 表主键
     *
     * @param menuId 资源表ID, sys_menu 表主键
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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