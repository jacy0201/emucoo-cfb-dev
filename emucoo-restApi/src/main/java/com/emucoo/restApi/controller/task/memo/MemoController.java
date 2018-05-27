package com.emucoo.restApi.controller.task.memo;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.task.*;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.models.enums.AppExecStatus;
import com.emucoo.restApi.sdk.token.ReSubmitTokenManager;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.sys.UserService;
import com.emucoo.service.task.LoopWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task/memo")
public class MemoController extends AppBaseController {

	@Autowired
    private LoopWorkService loopWorkService;

	@Autowired
    private UserService userService;

	/**
     *  取得防止重复提交的token
	 */
    @PostMapping("/fetchSubmitToken")
	public AppResult<SubmitTokenVo_O> fetchSubmitToken() {
		SubmitTokenVo_O submitToken = new SubmitTokenVo_O();
		submitToken.setSubmitToken(ReSubmitTokenManager.getToken());
	    return success(submitToken);
    }

    /**
     * 取联系人列表
     * @param params
     * @return
     */
    @PostMapping("/fetchContacts")
    public AppResult<ContactsVo_O> fetchContacts(@RequestBody ParamVo<ContactsVo_I> params) {
	    ContactsVo_I contactsVo_i = params.getData();
        ContactsVo_O result = userService.fetchContacts(contactsVo_i);
        return success(result);

    }

    /**
     * 编辑工作备忘
     * @param params
     * @return
     */
    @PostMapping("/editMemo")
	public AppResult editMemo(@RequestBody ParamVo<MemoEditVo_I> params) {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        MemoEditVo_I memoEditVo_I = params.getData();
	    String submitToken = memoEditVo_I.getSubmitToken();
	    if(ReSubmitTokenManager.validateToken(submitToken)){
	    	loopWorkService.editMemo(memoEditVo_I, user.getId());
	    	ReSubmitTokenManager.clearToken(submitToken);
	    	return success("success");
		} else {
	        return fail(AppExecStatus.SERVICE_ERR, "编辑工作备忘失败！");
		}
	}

    /**
     * 删除备忘
     * @param params
     * @return
     */
    @PostMapping("/deleteMemo")
    public AppResult deleteMemo(@RequestBody ParamVo<MemoDeleteVo_I> params) {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        MemoDeleteVo_I memoDeleteVo_I = params.getData();
        loopWorkService.deleteMemo(memoDeleteVo_I, user.getId());
        return success("success");

    }



    /**
     * 创建工作备忘
     * @param params
     * @return
     */
    @PostMapping("/createMemo")
    public AppResult createMemo(@RequestBody ParamVo<MemoCreationVo_I> params) {
        SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        MemoCreationVo_I memoCreationVo_I = params.getData();
        String submitToken = memoCreationVo_I.getSubmitToken();
        if(ReSubmitTokenManager.validateToken(submitToken)){
            loopWorkService.createMemo(memoCreationVo_I, user.getId());
            ReSubmitTokenManager.clearToken(submitToken);
            return success("success");
        } else {
            return fail(AppExecStatus.SERVICE_ERR, "创建任务失败！");
        }
    }


    /**
     * 查看工作备忘详细信息
     * @param userToken
     * @param params
     * @return
     */
    @PostMapping("/memoDetail")
	public AppResult<MemoDetailVo_O> memoDetail(@RequestBody ParamVo<MemoDetailVo_I> params) {
        SysUser loginUser = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
        MemoDetailVo_I voi = params.getData();
        MemoDetailVo_O result = loopWorkService.viewMemoDetail(voi.getWorkID(), voi.getSubID(), voi.getWorkType(), loginUser);
        if(result == null){
            return fail(AppExecStatus.SERVICE_ERR, "找不到对应的信息！");
        }

        return success(result);
	}


}
