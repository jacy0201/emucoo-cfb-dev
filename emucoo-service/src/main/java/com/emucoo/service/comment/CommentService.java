package com.emucoo.service.comment;

import com.emucoo.dto.modules.comment.*;
import com.emucoo.model.SysDept;
import com.emucoo.model.SysUser;

import java.util.List;

public interface CommentService {
    void add(CommentAddIn vo, SysUser user);

    void delete(CommentDeleteIn vo);

    CommentSelectOut selectCommentList(CommentSelectIn vo, SysUser user);

    ReplySelectOut selectReplyList(CommentSelectIn vo, SysUser user);
}
