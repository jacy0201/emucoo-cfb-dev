package com.emucoo.restApi.controller.comment;

import com.emucoo.dto.base.ParamVo;
import com.emucoo.dto.modules.comment.*;
import com.emucoo.model.SysUser;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jacy
 * 评论
 *
 */
@Api(description = "评论模块接口")
@RestController
@RequestMapping(value = "/api/comment")
public class CommentController extends AppBaseController {

	@Resource
	private CommentService commentService;

	/**
	 * 添加评论
	 * 
	 * @param
	 * @param base
	 */
	@ApiOperation(value = "添加评论/回复",notes = "" +
			"workType [ 1：常规任务，2：指派任务：3：改善任务，4：巡店任务，5：工作备忘，6：评论,7维修任务]" +
			"workType=1,2,3,5时,传workID 和 subID；"+
			"workType=4时,传reportID；" +
			"workType=6时,传commentID；"+
			"workType=7时,传repairID；")
	@PostMapping(value = "add")
	public AppResult add(@RequestBody ParamVo<CommentAddIn> base) {
		CommentAddIn vo = base.getData();
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		commentService.add(vo, user);
		return success("success");
	}

	/**
	 * 查询评论列表
	 * @param base
	 * @return
	 */
	@ApiOperation(value = "查询评论列表",notes = "" +
			"workType [ 1：常规任务，2：指派任务：3：改善任务，4：巡店任务，5：工作备忘，6：评论,7维修任务]" +
			"workType=1,2,3,5时,传workID 和 subID；"+
			"workType=4时,传reportID；" +
			"workType=6时,传commentID；"+
			"workType=7时,传repairID；")
	@PostMapping("getCommentList")
	public AppResult<CommentSelectOut> getCommentList(@RequestBody ParamVo<CommentSelectIn> base) {
		CommentSelectIn vo = base.getData();
		checkParam(vo.getWorkType(),"workType不能为空!");
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		CommentSelectOut commentSelectOut = commentService.selectCommentList(vo, user);
		return success(commentSelectOut);
	}

	/**
	 * 查询评论的回复
	 * @param base
	 * @return
	 */
	@ApiOperation(value = "查询回复列表" ,notes = ""+
			"workType [ 1：常规任务，2：指派任务：3：改善任务，4：巡店任务，5：工作备忘，6：评论,7维修任务]" +
			"workType=1,2,3,5时,传workID 和 subID；"+
			"workType=4时,传reportID；" +
			"workType=6时,传commentID；"+
			"workType=7时,传repairID；")
	@PostMapping("getReplyList")
	public AppResult<ReplySelectOut> getReplyList(@RequestBody ParamVo<CommentSelectIn> base) {
		CommentSelectIn vo = base.getData();
		checkParam(vo.getCommentID(),"commentID不能为空!");
		SysUser user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
		vo.setWorkType(6);
		ReplySelectOut outList = commentService.selectReplyList(vo, user);
		return success(outList);
	}

	/**
	 * 删除评论
	 *
	 * @param base
	 */
	@ApiOperation(value = "删除评论/回复")
	@PostMapping("delete")
	public AppResult delete(@RequestBody ParamVo<CommentDeleteIn> base) {
		CommentDeleteIn vo = base.getData();
		commentService.delete(vo);
		return success("success");
	}

}
