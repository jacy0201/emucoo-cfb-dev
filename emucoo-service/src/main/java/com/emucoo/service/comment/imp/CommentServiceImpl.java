package com.emucoo.service.comment.imp;

import com.emucoo.dto.modules.comment.CommentAddIn;
import com.emucoo.dto.modules.comment.CommentDeleteIn;
import com.emucoo.dto.modules.comment.CommentSelectIn;
import com.emucoo.dto.modules.comment.CommentSelectOut;
import com.emucoo.model.SysDept;
import com.emucoo.model.SysUser;
import com.emucoo.model.TTaskComment;
import com.emucoo.service.comment.CommentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public void add(CommentAddIn vo, SysUser user) {

    }

    @Override
    public void delete(CommentDeleteIn vo) {

    }

    @Override
    public List<CommentSelectOut> select(CommentSelectIn vo, SysUser user) {
        return null;
    }
}
