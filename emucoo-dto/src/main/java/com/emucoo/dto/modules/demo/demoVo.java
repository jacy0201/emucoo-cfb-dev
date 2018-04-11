package com.emucoo.dto.modules.demo;


import java.util.Date;

import javax.persistence.Column;

import lombok.Data;
@Data
public class demoVo {

	private Long id;
	
	/**
     * 内容分类ID
     */
    private Long contentCatId;

    /**
     * 图片
     */
    private String img;

    /**
     * 内容标题
     */
    private String title;

    /**
     * 子标题
     */
    private String subTitle;

    /**
     * 标题描述
     */
    private String titleDesc;

    /**
     * 内容
     */
    private String content;

    /**
     * 其他text输入  BANNER（链接），平台活动（作者名)
     */
    private String otherText;

    /**
     * 视频URL
     */
    private String videoUrl;
    
    /**
     * 优先级
     */
    private Integer orderNo;
    
    /**
     * 状态 0: 启用  1:禁用
     */
    private Integer effect;
    
    private Date createTime;

    private Date modifyTime;
}
