package com.emucoo.service.comment.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.comment.CommentAddIn;
import com.emucoo.dto.modules.comment.CommentDeleteIn;
import com.emucoo.dto.modules.comment.CommentSelectIn;
import com.emucoo.dto.modules.comment.CommentAddIn.ImgUrl;
import com.emucoo.mapper.CommentMapper;
import com.emucoo.model.Comment;
import com.emucoo.model.User;
import com.emucoo.service.comment.CommentService;

@Transactional
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public void add(CommentAddIn commentAddIn, User user) {
		Comment comment = new Comment();
		comment.setUnionId(commentAddIn.getWorkID().toString());
		comment.setUnionType(commentAddIn.getWorkType());
		comment.setUserId(user.getId());
		comment.setUserName(user.getUsername());
		comment.setIsShow(false);
		comment.setContent(commentAddIn.getReplyContent());
		List<ImgUrl> replyImgArr = commentAddIn.getReplyImgArr();
		if (replyImgArr != null && replyImgArr.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (ImgUrl imgUrl : replyImgArr) {
				sb.append(imgUrl.getImgUrl());
				sb.append(",");
			}
			String imgUrls = sb.toString().substring(0, sb.length() - 1);
			comment.setImgUrls(imgUrls);
		}
		commentMapper.add(comment);
	}

	@Override
	public void delete(CommentDeleteIn commentDeleteIn) {
		commentMapper.deleteById(commentDeleteIn.getReplyID());
	}

	@Override
	public List<Comment> select(CommentSelectIn commentSelectIn) {
		Comment comment = new Comment();
		comment.setUnionId(commentSelectIn.getWorkID().toString());
		comment.setUnionType(commentSelectIn.getWorkType());
		comment.setStartIndex((commentSelectIn.getPageNum() - 1) * commentSelectIn.getPageSize());
		comment.setPageSize(commentSelectIn.getPageSize());
		return commentMapper.select(comment);
	}
}
