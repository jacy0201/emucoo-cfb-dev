package com.emucoo.restApi.controller.comment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
import com.emucoo.model.Comment;
import com.emucoo.model.User;
import com.emucoo.restApi.controller.demo.AppBaseController;
import com.emucoo.restApi.controller.demo.AppResult;
import com.emucoo.restApi.sdk.token.UserTokenManager;
import com.emucoo.service.comment.CommentService;

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
		User user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
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
		vo.setPageNum(base.getPageNumber());
		vo.setPageSize(base.getPageSize());
		List<Comment> list = commentService.select(vo);
		List<CommentSelectOut> outList = new ArrayList<CommentSelectOut>();
		for (Comment comment : list) {
			CommentSelectOut out = new CommentSelectOut();
			out.setReplyID(comment.getId());
			out.setReplyContent(comment.getContent());
			out.setReplyTime(comment.getCreateTime().getTime());
			out.setAnswerID(comment.getUserId());
			out.setAnswerName(comment.getUserName());
			
			User user = UserTokenManager.getInstance().currUser(request.getHeader("userToken"));
			out.setAnswerHeadUrl(user.getHeadImgUrl());
			List<ImgUrl> imgList = new ArrayList<ImgUrl>();
			String[] str = comment.getImgUrls().split(",");
			for (int i = 0; i < str.length; i++) {
				ImgUrl imgUrl = new ImgUrl();
				imgUrl.setImgUrl(str[i]);
				imgList.add(imgUrl);
			}
			out.setReplyImgArr(imgList);
			outList.add(out);
		}
		return success(outList);
	}

}
