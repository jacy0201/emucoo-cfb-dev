package com.emucoo.dto.modules.RYGForm;

import com.emucoo.dto.modules.abilityForm.ProblemImg;
import com.emucoo.dto.modules.form.FormChanceVo;
import com.emucoo.dto.modules.form.FormSubProblemUnitVo;
import com.emucoo.dto.modules.form.FormSubProblemVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sj on 2018/4/24.
 */
@Data
@ApiModel
public class RYGFormProblemVo {
    @ApiModelProperty(value = "机会点数组", name = "chanceArray", required = true)
    private List<FormChanceVo> chanceArray;
    private Boolean isImportant;
    @ApiModelProperty(value = "是否NA", name = "isNA", required = true)
    private Boolean isNA;
    @ApiModelProperty(value = "其他机会点数组", name = "otherChanceArray")
    private List<FormChanceVo> otherChanceArray;
    @ApiModelProperty(value = "题目描述", name = "problemDescription")
    private String problemDescription;
    @ApiModelProperty(value = "题项id", name = "problemID", required = true, example = "1")
    private Long problemID;
    @ApiModelProperty(value = "题项名称", name = "problemName", required = true, example = "1.员工根据PJI标准制作出的比萨达到至少8分标准。")
    private String problemName;
    @ApiModelProperty(value = "题项类型 1-检查类，2-抽查类", name = "problemType", example = "2")
    private Integer problemType;
    @ApiModelProperty(value = "描述照片数组", name = "descImgArr")
    private List<ProblemImg> descImgArr;
    @ApiModelProperty(value = "检查结果", name = "notes")
    private String notes;
    @ApiModelProperty(value = "题项结果类型 1-绿，2-黄，3-红", name = "problemResultType", example = "2")
    private Integer resultType;
    @ApiModelProperty(value = "是否完成", name = "isDone", required = true)
    private Boolean isDone;
}
