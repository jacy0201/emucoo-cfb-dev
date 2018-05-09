package com.emucoo.manager.controller;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.TLoopPlan;
import com.emucoo.model.TPlanFormRelation;
import com.emucoo.service.manage.LoopPlanManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sj on 2018/4/13.
 */
@Controller
@Api(description = "计划管理")
@RequestMapping(value = "planManage")
public class PlanManageController extends BaseResource {

    @Autowired
    private LoopPlanManageService loopPlanManageService;

    /**
     * 新增计划
     * @param param
     * @return
     */

    @ApiOperation(value = "新增计划")
    @PostMapping(value = "addPlan")
    @ResponseBody
    public ApiResult<String> addPlan(@RequestBody ParamVo<TLoopPlan> param){
        TLoopPlan plan = param.getData();
        if(plan == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "业务数据不能为空！");
        }
        if(StringUtils.isBlank(plan.getName())) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划名不能为空！");
        }
        if (plan.getDptId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "请选择部门！");
        }
        List<TPlanFormRelation> planFormRelationList = plan.getPlanFormRelationList();
        if(planFormRelationList == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "请选择表单！");
        }
        for(TPlanFormRelation tPlanFormRelation : planFormRelationList) {
            if(tPlanFormRelation.getFormMainId() == null) {
                return fail(ApiExecStatus.INVALID_PARAM, "请选择表单！");
            }
        }
        loopPlanManageService.addPlan(plan);
        return success("success");
    }

    /**
     * 编辑计划
     * @param param
     * @return
     */
    @ApiOperation(value = "更新计划")
    //@ApiImplicitParams({@ApiImplicitParam(dataType = "Long", name = "id", value = "计划id，必填", required = true)})
    @PostMapping(value = "updatePlanById")
    @ResponseBody
    public ApiResult<String> updatePlanById(@RequestBody ParamVo<TLoopPlan> param) {
        TLoopPlan plan = param.getData();
        if (plan == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "业务数据不能为空！");
        }
        if(plan.getId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划id不能为空！");
        }
        if (StringUtils.isBlank(plan.getName())) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划名不能为空！");
        }
        if (plan.getDptId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "请选择部门！");
        }
        List<TPlanFormRelation> planFormRelationList = plan.getPlanFormRelationList();
        if (planFormRelationList == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "请选择表单！");
        }
        for (TPlanFormRelation tPlanFormRelation : planFormRelationList) {
            if (tPlanFormRelation.getFormMainId() == null) {
                return fail(ApiExecStatus.INVALID_PARAM, "请选择表单！");
            }
        }
        loopPlanManageService.updatePlanById(plan);
        return success("success");
    }

    /**
     * 启动计划
     * @param param
     * @return
     */
    @ApiOperation(value = "启用/停用计划")
    //@ApiImplicitParams({@ApiImplicitParam(dataType = "Long", name = "id", value = "计划id，必填", required = true)})
    @PostMapping(value = "modifyPlanUseById")
    @ResponseBody
    public ApiResult<String> startPlanById(@RequestBody ParamVo<TLoopPlan> param) {
        TLoopPlan plan = param.getData();
        if (plan.getId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划id不能为空！");
        }
        if (plan.getIsUse() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划使用状态不能为空！");
        }
        loopPlanManageService.modifyPlanUseById(plan);
        return success("success");
    }

    /**
     * 停用计划
     *
     * @param param
     * @return
     */
    /*@ApiOperation(value = "停用计划")
    @ApiImplicitParams({@ApiImplicitParam(dataType = "Long", name = "id", value = "计划id，必填", required = true)})
    @PostMapping(value = "stopPlanById")
    @ResponseBody
    public ApiResult<String> stopPlanById(@RequestBody ParamVo<TLoopPlan> param) {
        TLoopPlan plan = param.getData();
        if (plan.getId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划id不能为空！");
        }
        loopPlanManageService.stopPlanById(plan);
        return success("success");
    }*/

    /**
     * 删除计划
     * @param param
     * @return
     */
    @ApiOperation(value = "删除计划")
    //@ApiImplicitParams({@ApiImplicitParam(dataType = "Long", name = "id", value = "计划id，必填", required = true)})
    @PostMapping(value = "deletePlanById")
    @ResponseBody
    public ApiResult<String> deletePlanById(@RequestBody ParamVo<TLoopPlan> param) {
        TLoopPlan plan = param.getData();
        if (plan.getId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划id不能为空！");
        }
        loopPlanManageService.deletePlanById(plan);
        return success("success");
    }

    /**
     * 根据条件搜索计划列表
     * @param param
     * @return
     */
    @ApiOperation(value = "根据条件查询计划列表", response = TLoopPlan.class)
    @PostMapping(value = "planListByCondition")
    @ResponseBody
    public ApiResult<PageInfo> findPlanListByCondition(@RequestBody ParamVo<TLoopPlan> param) {
        if(param.getPageNumber() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "当前页码不能为空！");
        }
        if (param.getPageSize() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "页记录数不能为空！");
        }
        TLoopPlan plan = param.getData();
        PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "create_time desc");
        List<TLoopPlan> tLoopPlans = loopPlanManageService.findPlanListByCondition(plan);
        return success(new PageInfo<>(tLoopPlans));
    }

    /**
     * 根据id查询计划详情
     * @param param
     * @return
     */
    @ApiOperation(value = "根据id查询计划详情", response = TLoopPlan.class)
    //@ApiImplicitParams({@ApiImplicitParam(dataType = "Long", name = "id", value = "计划id，必填", required = true)})
    @PostMapping(value = "findPlanById")
    @ResponseBody
    public ApiResult findPlanById(@RequestBody ParamVo<TLoopPlan> param) {
        TLoopPlan plan = param.getData();
        if (plan.getId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划id不能为空！");
        }
        TLoopPlan tLoopPlan = loopPlanManageService.findPlanById(plan);
        return success(tLoopPlan);
    }
}
