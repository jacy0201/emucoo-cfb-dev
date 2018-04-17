package com.emucoo.restApi.controller.comment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.emucoo.model.SysDept;
import com.emucoo.model.SysUser;
import com.emucoo.model.TTaskComment;
import com.emucoo.service.comment.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.comment.CommentAddIn;
import com.emucoo.dto.modules.comment.CommentDeleteIn;
import com.emucoo.dto.modules.comment.CommentSelectIn;
import com.emucoo.dto.modules.comment.CommentSelectOut;
import com.emucoo.dto.modules.comment.CommentSelectOut.ImgUrl;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;

/**
 * 
 * @author zhangxq
 * @date 2018-03-17
 */
@RestController
@RequestMapping(value = "/api/comment")
public class CommentControlller extends AppBaseController {

	@Resource
	private CommentService commentService;

	/**
	 * 事务回复
	 * 
	 * @param
	 * @param base
	 */
	@PostMapping(value = "add")
	public AppResult<String> add(@RequestBody ParamVo<CommentAddIn> base) {
		CommentAddIn vo = base.getData();
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		commentService.add(vo, user);
		return success("");
	}

	/**
	 * 事务回复删除
	 * 
	 * @param base
	 */
	@PostMapping("delete")
	public AppResult<String> delete(@RequestBody ParamVo<CommentDeleteIn> base) {
		CommentDeleteIn vo = base.getData();
		commentService.delete(vo);
		return success("");
	}

	/**
	 * 分页查看回复
	 * 
	 * @param base
	 * @return
	 */
	@PostMapping("select")
	public AppResult<List<CommentSelectOut>> select(@RequestBody ParamVo<CommentSelectIn> base) {
		CommentSelectIn vo = base.getData();
		vo.setPageNum(Integer.toUnsignedLong(base.getPageNumber()));
		vo.setPageSize(Integer.toUnsignedLong(base.getPageSize()));
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		List<CommentSelectOut> outList = commentService.select(vo, user);

		return success(outList);
	}

}
