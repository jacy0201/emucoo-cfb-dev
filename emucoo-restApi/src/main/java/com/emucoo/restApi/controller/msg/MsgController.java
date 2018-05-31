package com.emucoo.restApi.controller.msg;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.msg.MsgSummaryOut;
import com.emucoo.dto.modules.shop.CheckSheetVo;
import com.emucoo.dto.modules.shop.CheckSheetVo_O;
import com.emucoo.dto.modules.shop.PlanArrangeGetFormIn;
import com.emucoo.model.SysUser;
import com.emucoo.model.TFormMain;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.msg.MsgService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/msgSummary")
    public AppResult<MsgSummaryOut> msgSummary(HttpServletRequest request) {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        MsgSummaryOut msgSummaryOut = msgService.msgSummary(user);
        return success(msgSummaryOut);
    }
}
