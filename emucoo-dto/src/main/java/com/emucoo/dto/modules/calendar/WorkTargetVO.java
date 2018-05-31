package com.emucoo.dto.modules.calendar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value="工作目标返回参数")
public class WorkTargetVO {
    @ApiModelProperty(value="工作目标id",name="workTargetID")
    private Long workTargetID;

    @ApiModelProperty(value="月份",name="month",required = true,example ="2018-05" )
    private String month;
    /**
     * kpi
     */
    @ApiModelProperty(value="KPI目标",name="kpi")
    private String kpi;
    /**
     * 工作重点
     */
    @ApiModelProperty(value="工作重点",name="workContent")
    private String workContent;
    /**
     * 进货时间
     */
    @ApiModelProperty(value="进货时间",name="purchaseDate",example ="2018/05/29")
    private String purchaseDate;

    @ApiModelProperty(value="销售目标集合",name="saleList",notes = "编辑/添加 月工作目标时传此参数")
    private  List<SaleVO> saleList;

    //直营店
    private List<SaleVO> saleDirectList;
    private double totalDirectTarget=0;
    private double totalDirectActual=0;
    //加盟店
    private List<SaleVO> saleJoinList;
    private double totalJoinTarget=0;
    private double totalJoinActual=0;

    @Data
    public static class SaleVO{

        //销售目标ID
        @ApiModelProperty(value="销售目标id",name="saleTargetID")
        private Long saleTargetID;
        @ApiModelProperty(value="店铺id",name="shopId")
        private Long shopId;
        @ApiModelProperty(value="店铺名称",name="shopName")
        private String shopName;
        //1-直营；2-加盟
        @ApiModelProperty(value="店铺类型",name="shopType",notes = "1-直营；2-加盟")
        private Integer shopType;

        //目标金额
        @ApiModelProperty(value="目标金额",name="targetAmount")
        private Double targetAmount;

        //实际金额
        @ApiModelProperty(value="实际金额",name="actualAmount")
        private Double actualAmount;

    }



}
