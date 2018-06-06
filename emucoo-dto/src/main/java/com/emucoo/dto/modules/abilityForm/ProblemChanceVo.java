package com.emucoo.dto.modules.abilityForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sj on 2018/5/17.
 */
@ApiModel
public class ProblemChanceVo {
    @ApiModelProperty(value = "机会点名", name = "chanceName", example = "玻璃脏")
    private String chanceName;
    @ApiModelProperty(value = "机会点id", name = "chanceID", example = "2")
    private Long chanceID;
    @ApiModelProperty(value = "是否被触发", name = "isPick", example = "true")
    private Boolean isPick;

    public void setChanceName(String chanceName) {
        this.chanceName = chanceName;
    }

    public String getChanceName() {
        return chanceName;
    }

    public Long getChanceID() {
        return chanceID;
    }

    public void setChanceID(Long chanceID) {
        this.chanceID = chanceID;
    }

    public void setIsPick(Boolean isPick) {
        this.isPick = isPick;
    }

    public Boolean getIsPick() {
        return isPick;
    }

}
