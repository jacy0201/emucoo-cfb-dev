package com.emucoo.dto.modules.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserVo {

    /**
     * 用户Id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户描述
     */
    private String remark;

    /**
     * 部门id ,关联sys_dept表主键
     */
    private Long dptId;

    /**
     * 部门名称
     */
    private String dptName;

    /**
     * 店铺信息
     */
    private List<ShopInfo> shopList=new ArrayList<ShopInfo>();


    /**
     * 岗位信息
     */
    private List<PostInfo> postList=new ArrayList<PostInfo>();


    /**
     * 分区信息
     */
    private List<AreaInfo> AreaList=new ArrayList<AreaInfo>();


    /**
     * 品牌信息
     */
    private List<BrandInfo> BrandList=new ArrayList<BrandInfo>();


    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别： 1-男  ； 2-女 ；3-其他
     */
    private Byte sex;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否锁 0：正常 1：锁定
     */
    private Boolean isLock;

    /**
     * 0：停用     1：启用
     */
    private Boolean isUse;

    /**
     * 是否是管理员1：是管理员 其它不是管理员
     */
    private Boolean isAdmin;

    /**
     * 是否逻辑删除0：正常1：逻辑删除
     */
    private Boolean isDel;

    private Long orgId;

    @Data
    public class ShopInfo{
        /**
         * 店铺id
         */
        private Long shopId;

        /**
         * 店铺名称
         */
        private String shopName;
    }

    @Data
    public class PostInfo{

        /**
         * 岗位id
         */
        private String postId;

        /**
         * 岗位名称
         */
        private String postName;

    }

    @Data
    public class AreaInfo{

        /**
         * 分区id
         */
        private String areaId;

        /**
         * 分区名称
         */
        private String areaName;

    }


    @Data
    public class BrandInfo{

        /**
         * 品牌id
         */
        private String brandId;

        /**
         * 品牌名称
         */
        private String brandName;

    }

}
