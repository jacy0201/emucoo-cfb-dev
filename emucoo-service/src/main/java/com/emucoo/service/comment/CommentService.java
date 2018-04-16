package com.emucoo.service.comment;

import com.emucoo.dto.modules.comment.CommentAddIn;
import com.emucoo.dto.modules.comment.CommentDeleteIn;
import com.emucoo.dto.modules.comment.CommentSelectIn;
import com.emucoo.dto.modules.comment.CommentSelectOut;
import com.emucoo.model.SysDept;
import com.emucoo.model.SysUser;

import java.util.List;

public interface CommentService {
    void add(CommentAddIn vo, SysUser user);

    void delete(CommentDeleteIn vo);

    List<CommentSelectOut> select(CommentSelectIn vo, SysUser user);
}
