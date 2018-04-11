package com.emucoo.service.comment;

import java.util.List;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.comment.CommentAddIn;
import com.emucoo.dto.modules.comment.CommentDeleteIn;
import com.emucoo.dto.modules.comment.CommentSelectIn;
import com.emucoo.model.Comment;
import com.emucoo.model.User;

/**
 * 
 * @author zhangxq
 * @date 2018-03-17
 */
public interface CommentService extends BaseService<Comment> {
   
	void add(CommentAddIn commentAddIn, User user);
	
	void delete(CommentDeleteIn commentDeleteIn);
	
	List<Comment> select(CommentSelectIn commentSelectIn);
}
