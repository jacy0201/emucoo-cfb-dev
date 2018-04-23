package com.emucoo.service.comment.imp;

import com.emucoo.dto.modules.comment.CommentAddIn;
import com.emucoo.dto.modules.comment.CommentDeleteIn;
import com.emucoo.dto.modules.comment.CommentSelectIn;
import com.emucoo.dto.modules.comment.CommentSelectOut;
import com.emucoo.mapper.TFileMapper;
import com.emucoo.mapper.TTaskCommentMapper;
import com.emucoo.model.SysDept;
import com.emucoo.model.SysUser;
import com.emucoo.model.TFile;
import com.emucoo.model.TTaskComment;
import com.emucoo.service.comment.CommentService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private TTaskCommentMapper taskCommentMapper;

    @Autowired
    private TFileMapper fileMapper;

    @Override
    public void add(CommentAddIn vo, SysUser user) {
        TTaskComment comment = new TTaskComment();
        comment.setContent(vo.getReplyContent());
        comment.setUnionId(Long.parseLong(vo.getWorkID()));
        comment.setUnionType(vo.getWorkType());
        comment.setUserId(user.getId());
        comment.setUserName(user.getRealName());
        comment.setDel(false);
        comment.setShow(true);
        comment.setCreateUserId(user.getId());
        comment.setCreateTime(DateUtil.currentDate());
        comment.setModifyUserId(user.getId());
        comment.setModifyTime(DateUtil.currentDate());
        if(vo.getReplyImgArr() != null && vo.getReplyImgArr().size() > 0) {
            List<CommentAddIn.ImgUrl> imgurls = vo.getReplyImgArr();
            List<String> ids = new ArrayList<>();
            List<TFile> imgs = new ArrayList<>();
            for(CommentAddIn.ImgUrl imgUrl : imgurls) {
                TFile img = new TFile();
                img.setImgUrl(imgUrl.getImgUrl());
                imgs.add(img);
            }
            fileMapper.insertList(imgs);
            for(TFile iid : imgs) {
                if(iid.getId() == null)
                    continue;
                ids.add(Long.toString(iid.getId()));
            }
            comment.setImgIds(StringUtils.join(ids, ","));
        }
        taskCommentMapper.insert(comment);
    }

    @Override
    public void delete(CommentDeleteIn vo) {
        taskCommentMapper.deleteByPrimaryKey(vo.getReplyID());
    }

    @Override
    public List<CommentSelectOut> select(CommentSelectIn vo, SysUser user) {
		return taskCommentMapper.selectByPages(vo.getWorkID(), vo.getWorkType(), (vo.getPageNum() - 1) * vo.getPageSize(), vo.getPageSize());
    }
}
