package com.emucoo.restApi.controller.msg;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.msg.MsgDetailIn;
import com.emucoo.dto.modules.msg.MsgListIn;
import com.emucoo.dto.modules.msg.MsgListOut;
import com.emucoo.dto.modules.msg.MsgSummaryOut;
import com.emucoo.dto.modules.msg.UpdateMsgReadedIn;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.msg.MsgService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sj on 2018/5/30.
 */
@RestController
@Api(description = "消息中心")
@RequestMapping("/api/msg/")
public class MsgController extends AppBaseController {

    @Autowired
    private MsgService msgService;

    @ApiOperation(value = "获取消息总概览")
    @PostMapping("/msgSummary")
    public AppResult<MsgSummaryOut> msgSummary(HttpServletRequest request) {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        MsgSummaryOut msgSummaryOut = msgService.msgSummary(user);
        return success(msgSummaryOut);
    }


    @ApiOperation(value = "获取指定功能的消息列表")
    @PostMapping("/msgList")
    public AppResult<MsgListOut> msgList(HttpServletRequest request, @RequestBody ParamVo<MsgListIn> params) {
        MsgListIn msgListIn = params.getData();
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        PageHelper.startPage(params.getPageNumber(), params.getPageSize(), "create_time desc");
        MsgListOut msgList = msgService.getMsgList(user, msgListIn);
        return success(msgList);
    }

    @ApiOperation(value = "更新消息列表未读状态")
    @PostMapping("/updateMsgListReaded")
    public AppResult<String> updateMsgListReaded(HttpServletRequest request, @RequestBody ParamVo<UpdateMsgReadedIn> params) {
        UpdateMsgReadedIn updateMsgIn = params.getData();
        checkParam(updateMsgIn.getMsgIDs(), "消息不能为空！");
        msgService.updateMsgListReaded(updateMsgIn);
        return success("ok");
    }

    /*@ApiOperation(value = "查看消息详情")
    @PostMapping("/msgDetail")
    public String msgDetail(HttpServletRequest request, @RequestBody ParamVo<MsgDetailIn> params) {
        MsgDetailIn msgDetailIn = params.getData();
        checkParam(msgDetailIn.getMsgID(), "消息id不能为空！");
        String forwardUrl = msgService.msgDetail(msgDetailIn);

        return forwardUrl;
    }*/

}
