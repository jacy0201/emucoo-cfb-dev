package com.emucoo.restApi.controller.comment;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.comment.*;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.comment.CommentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论
 */
@Api(description = "评论模块接口")
@RestController
@RequestMapping(value = "/api/comment")
public class CommentControlller extends AppBaseController {

	@Resource
	private CommentService commentService;

	/**
	 * 添加评论
	 * 
	 * @param
	 * @param base
	 */
	@PostMapping(value = "add")
	public AppResult add(@RequestBody ParamVo<CommentAddIn> base) {
		CommentAddIn vo = base.getData();
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		commentService.add(vo, user);
		return success("success");
	}

	/**
	 * 查询评论
	 * @param base
	 * @return
	 */
	@PostMapping("getCommentList")
	public AppResult<List<CommentSelectOut>> getCommentList(@RequestBody ParamVo<CommentSelectIn> base) {
		CommentSelectIn vo = base.getData();
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		List<CommentSelectOut> outList = commentService.select(vo, user);
		return success(outList);
	}

	/**
	 * 查询评论的回复
	 * @param base
	 * @return
	 */
	@PostMapping("getReplyList")
	public AppResult<List<CommentSelectOut>> getReplyList(@RequestBody ParamVo<ReplySelectIn> base) {
		ReplySelectIn replySelectIn = base.getData();
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		CommentSelectIn vo=new CommentSelectIn();
		vo.setCommentID(replySelectIn.getCommentID());
		vo.setWorkType(5);
		List<CommentSelectOut> outList = commentService.select(vo, user);
		return success(outList);
	}

	/**
	 * 删除评论
	 *
	 * @param base
	 */
	@PostMapping("delete")
	public AppResult<String> delete(@RequestBody ParamVo<CommentDeleteIn> base) {
		CommentDeleteIn vo = base.getData();
		commentService.delete(vo);
		return success("success");
	}

}
