package com.emucoo.restApi.controller.answer;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.answer.AnswerAddIn;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.answer.AnswerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 
 * @author zhangxq
 * @date 2018-03-23
 */
@RestController
@RequestMapping(value = "/api/answer")
public class AnswerControlller extends AppBaseController {

	@Resource
	private AnswerService answerService;

	/**
	 * 事务回复
	 * 
	 * @param
	 * @param base
	 */
	@PostMapping(value = "add")
	public AppResult<String> add(@RequestBody ParamVo<AnswerAddIn> base) {
		AnswerAddIn vo = base.getData();
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		answerService.add(vo, user);
		return success("");
	}

}
