package com.emucoo.dto.modules.abilityForm;

/**
 * Created by sj on 2018/5/17.
 */
public class ProblemChanceVo {
    private String chanceName;
    private Long chanceID;
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
