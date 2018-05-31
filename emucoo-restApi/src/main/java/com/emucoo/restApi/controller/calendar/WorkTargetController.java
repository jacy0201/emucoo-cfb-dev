package com.emucoo.restApi.controller.calendar;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.calendar.WorkTargetDelVO;
import com.emucoo.dto.modules.calendar.WorkTargetQueryVO;
import com.emucoo.dto.modules.calendar.WorkTargetVO;
import com.emucoo.model.SysUser;
import com.emucoo.model.TWorkTarget;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.calendar.WorkTargetService;
import com.emucoo.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


/**
 * @author Jacy
 * 月工作目标
 */
@RestController
@Api(description = "月工作目标接口")
@RequestMapping("/api/target")
public class WorkTargetController extends AppBaseController {

    @Autowired
    private WorkTargetService workTargetService;

    /**
     * 获取用户月工作目标
     * @param params
     * @return
     */
    @ApiOperation(value = "获取用户月工作目标")
    @PostMapping(value = "/getWorkTarget")
    public AppResult getWorkTarget(@RequestBody ParamVo<WorkTargetQueryVO> params) {
        WorkTargetQueryVO workTargetQueryVO= params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        Long currentUserId = user.getId();
        if (null == workTargetQueryVO.getUserID()) {
            workTargetQueryVO.setUserID(currentUserId);
        }
        if (null == workTargetQueryVO.getMonth()) {
            workTargetQueryVO.setMonth(DateUtil.simple4(new Date()));
        }
        WorkTargetVO workTargetVO=workTargetService.getWorkTarget(workTargetQueryVO);
        return success(workTargetVO);
    }

    /**
     * 添加工作目标
     * @param params
     * @return
     */
    @ApiOperation(value = "添加工作目标")
    @PostMapping(value = "/addWorkTarget")
    public AppResult addWorkTarget(@RequestBody ParamVo<WorkTargetVO> params) {
        WorkTargetVO workTargetVO = params.getData();
        Example example=new Example(TWorkTarget.class);
        example.createCriteria().andEqualTo("month",workTargetVO.getMonth()).andEqualTo("isDel",false);
        List list=workTargetService.selectByExample(example);
        if(null!=list && list.size()>0){
            return fail(AppExecStatus.INVALID_PARAM,"该月工作目标已存在!");
        }
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        workTargetService.addWorkTarget(workTargetVO,user.getId());
        return success("success");
    }

    /**
     * 编辑工作目标
     * @param params
     * @return
     */
    @ApiOperation(value = "编辑工作目标")
    @PostMapping(value = "/editWorkTarget")
    public AppResult editWorkTarget(@RequestBody ParamVo<WorkTargetVO> params) {
        WorkTargetVO workTargetVO = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        workTargetService.editWorkTarget(workTargetVO,user.getId());
        return success("success");
    }


    /**
     * 删除工作目标
     *
     */
    @ApiOperation(value = "删除工作目标")
    @PostMapping(value = "/deleteWorkTarget")
    public AppResult deleteWorkTarget(@RequestBody ParamVo<WorkTargetDelVO> params) {
        WorkTargetDelVO workTargetDelVO=params.getData();
        checkParam(workTargetDelVO.getWorkTargetId(),"workTargetId不能为空!");
        workTargetService.deleteWorkTarget(workTargetDelVO);
        return success("success");
    }

}
