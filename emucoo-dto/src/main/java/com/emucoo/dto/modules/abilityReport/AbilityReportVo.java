package com.emucoo.dto.modules.abilityReport;

import com.emucoo.dto.modules.abilityForm.AbilitySubForm;
import com.emucoo.dto.modules.abilityForm.AbilitySubFormKind;
import com.emucoo.dto.modules.report.ChancePointVo;

import java.util.List;

/**
 * Created by sj on 2018/5/22.
 */
public class AbilityReportVo {
    private String shopName;
    private String shopownerName;
    private String checkDate;
    private String inspectorName;
    private String inspectorPosition;
    private String checkDepartmentName;
    private String resultName;
    private List<AbilitySubFormKind> resultKindArray;
    private List<ChancePointVo> chancePointArr;

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopownerName(String shopownerName) {
        this.shopownerName = shopownerName;
    }

    public String getShopownerName() {
        return shopownerName;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorPosition(String inspectorPosition) {
        this.inspectorPosition = inspectorPosition;
    }

    public String getInspectorPosition() {
        return inspectorPosition;
    }

    public void setCheckDepartmentName(String checkDepartmentName) {
        this.checkDepartmentName = checkDepartmentName;
    }

    public String getCheckDepartmentName() {
        return checkDepartmentName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getResultName() {
        return resultName;
    }

    public List<AbilitySubFormKind> getResultKindArray() {
        return resultKindArray;
    }

    public void setResultKindArray(List<AbilitySubFormKind> resultKindArray) {
        this.resultKindArray = resultKindArray;
    }

    public List<ChancePointVo> getChancePointArr() {
        return chancePointArr;
    }

    public void setChancePointArr(List<ChancePointVo> chancePointArr) {
        this.chancePointArr = chancePointArr;
    }
}
