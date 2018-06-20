package com.emucoo.service.comment;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.dto.modules.comment.*;
import com.emucoo.model.SysUser;
import com.emucoo.model.TTaskComment;

public interface CommentService  extends BaseService<TTaskComment>{
    void add(CommentAddIn vo, SysUser user);

    void delete(CommentDeleteIn vo);

    CommentSelectOut selectCommentList(CommentSelectIn vo, SysUser user);

    ReplySelectOut selectReplyList(CommentSelectIn vo, SysUser user);
}
