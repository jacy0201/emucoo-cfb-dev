package com.emucoo.manager.controller;

import com.emucoo.common.base.rest.ApiExecStatus;
import com.emucoo.common.base.rest.ApiResult;
import com.emucoo.common.base.rest.BaseResource;
import com.emucoo.common.exception.ApiException;
import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.base.ParamVo;
import com.emucoo.model.TLoopPlan;
import com.emucoo.model.TPlanFormRelation;
import com.emucoo.service.manage.TLoopPlanManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@RequestMapping(value = "planManage")
public class PlanManageController extends BaseResource {

    @Autowired
    private TLoopPlanManageService tLoopPlanManageService;

    /**
     * 新增计划
     * @param param
     * @return
     */
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
        tLoopPlanManageService.addPlan(plan);
        return success("success");
    }

    /**
     * 编辑计划
     * @param param
     * @return
     */
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
        tLoopPlanManageService.updatePlanById(plan);
        return success("success");
    }

    /**
     * 启动计划
     * @param param
     * @return
     */
    @PostMapping(value = "startPlanById")
    @ResponseBody
    public ApiResult<String> startPlanById(@RequestBody ParamVo<TLoopPlan> param) {
        TLoopPlan plan = param.getData();
        if (plan.getId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划id不能为空！");
        }
        tLoopPlanManageService.startPlanById(plan);
        return success("success");
    }

    /**
     * 停用计划
     *
     * @param param
     * @return
     */
    @PostMapping(value = "stopPlanById")
    @ResponseBody
    public ApiResult<String> stopPlanById(@RequestBody ParamVo<TLoopPlan> param) {
        TLoopPlan plan = param.getData();
        if (plan.getId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划id不能为空！");
        }
        tLoopPlanManageService.stopPlanById(plan);
        return success("success");
    }

    @PostMapping(value = "deletePlanById")
    @ResponseBody
    public ApiResult<String> deletePlanById(@RequestBody ParamVo<TLoopPlan> param) {
        TLoopPlan plan = param.getData();
        if (plan.getId() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "计划id不能为空！");
        }
        tLoopPlanManageService.deletePlanById(plan);
        return success("success");
    }

    /**
     * 根据条件搜索计划列表
     * @param param
     * @return
     */
    @PostMapping(value = "planListByCondition")
    @ResponseBody
    public ApiResult findPlanListByCondition(@RequestBody ParamVo<TLoopPlan> param) {
        if(param.getPageNumber() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "当前页码不能为空！");
        }
        if (param.getPageSize() == null) {
            return fail(ApiExecStatus.INVALID_PARAM, "页记录数不能为空！");
        }
        TLoopPlan plan = param.getData();
        PageHelper.startPage(param.getPageNumber(), param.getPageSize(), "create_time desc");
        List<TLoopPlan> tLoopPlans = tLoopPlanManageService.findPlanListByCondition(plan);
        return success(new PageInfo<>(tLoopPlans));
    }
}
