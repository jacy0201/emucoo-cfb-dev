package com.emucoo.service.comment.imp;

import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.modules.comment.CommentAddIn;
import com.emucoo.dto.modules.comment.CommentDeleteIn;
import com.emucoo.dto.modules.comment.CommentSelectIn;
import com.emucoo.dto.modules.comment.CommentSelectOut;
import com.emucoo.mapper.SysUserMapper;
import com.emucoo.mapper.TFileMapper;
import com.emucoo.mapper.TLoopWorkMapper;
import com.emucoo.mapper.TTaskCommentMapper;
import com.emucoo.model.SysUser;
import com.emucoo.model.TFile;
import com.emucoo.model.TLoopWork;
import com.emucoo.model.TTaskComment;
import com.emucoo.service.comment.CommentService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Resource
    private TTaskCommentMapper taskCommentMapper;
    @Resource
    private TFileMapper fileMapper;
    @Resource
    private TLoopWorkMapper tLoopWorkMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public void add(CommentAddIn vo, SysUser user) {
        TTaskComment comment = new TTaskComment();
        comment.setContent(vo.getContent());
        Integer workType=vo.getWorkType();
        comment.setUnionType(workType);
        Long unionId=null;
        //如果workType 为 常规、指派、改善 、备忘 任务类型，则根据 workID 和 subID 获取任务主键ID
        if (workType == 1 || workType == 2 || workType == 3 || workType==5){
            TLoopWork tLoopWork=new TLoopWork();
            tLoopWork.setWorkId(vo.getWorkID());
            tLoopWork.setSubWorkId(vo.getSubID());
            TLoopWork loopWork= tLoopWorkMapper.selectOne(tLoopWork);
            unionId=loopWork.getId();
        }else if(workType == 4){// workType==4 巡店，巡店的评论对象是报告
            unionId=vo.getReportID();
        }else if(workType == 6){//workType==6 评论， 针对评论的 回复
            unionId=vo.getCommentID();
        }else if(workType ==7){//workType==7 维修任务
            unionId=vo.getRepairID();
        }
        comment.setUnionId(unionId);
        comment.setUserId(user.getId());
        comment.setUserName(user.getRealName());
        comment.setDel(false);
        comment.setShow(true);
        comment.setCreateUserId(user.getId());
        comment.setCreateTime(DateUtil.currentDate());
        comment.setModifyUserId(user.getId());
        comment.setModifyTime(DateUtil.currentDate());
        if(null!=vo.getCommentImgArr() && vo.getCommentImgArr().size() > 0) {
            List<String> timgids = new ArrayList<String>();
            vo.getCommentImgArr().forEach(imageUrlVo -> {
                TFile timg = new TFile();
                timg.setImgUrl(imageUrlVo.getImgUrl());
                timg.setCreateTime(DateUtil.currentDate());
                timg.setModifyTime(DateUtil.currentDate());
                timg.setCreateUserId(user.getId());
                fileMapper.insert(timg);
                timgids.add(Long.toString(timg.getId()));
            });
            comment.setImgIds(StringUtils.join(timgids, ","));
        }
        taskCommentMapper.insertSelective(comment);
    }

    @Override
    public void delete(CommentDeleteIn vo) {
        taskCommentMapper.deleteByPrimaryKey(vo.getCommentId());
    }

    /**
     * 查询评论/回复
     * @param vo
     * @param user
     * @return
     */
    @Override
    public List<CommentSelectOut> select(CommentSelectIn vo, SysUser user) {
        String workId=vo.getWorkID();
        Integer workType=vo.getWorkType();
        String  subId=vo.getSubID();
        Long  reportId=vo.getReportID();
        Long commentId=vo.getCommentID();
        List<CommentSelectOut> list=null;
        Long unionId=null;
        //workType=6 评论，查询类型为评论的评论 ，即查询回复
        if(6==workType){//查询回复
            unionId=commentId;
        }else{//查询评论
            if (workType == 1 || workType == 2 || workType == 3|| workType==5){
                TLoopWork tLoopWork=new TLoopWork();
                tLoopWork.setWorkId(workId);
                tLoopWork.setSubWorkId(subId);
                TLoopWork loopWork= tLoopWorkMapper.selectOne(tLoopWork);
                unionId=loopWork.getId();
            }else if(workType==4){
                unionId=reportId;
            }
        }
        Example example=new Example(TTaskComment.class);
        example.createCriteria().andEqualTo("unionId",unionId).andEqualTo("unionType",workType)
                .andEqualTo("isDel",true).andEqualTo("isShow",true);
        example.setOrderByClause("create_time desc");
        List<TTaskComment> commentList=taskCommentMapper.selectByExample(example);
        if(null!=commentList && commentList.size()>0){
            for(TTaskComment tTaskComment:commentList){
                CommentSelectOut commentSelectOut=new CommentSelectOut();
                commentSelectOut.setCommentContent(tTaskComment.getContent());
                commentSelectOut.setCommentID(tTaskComment.getId());
                commentSelectOut.setCommentTime(tTaskComment.getCreateTime().getTime());
                commentSelectOut.setCommentUserID(tTaskComment.getUserId());
                commentSelectOut.setCommentUserName(tTaskComment.getUserName());
                commentSelectOut.setCommentHeadUrl(sysUserMapper.selectByPrimaryKey(tTaskComment.getUserId()).getHeadImgUrl());
                 //设置评论照片
                if(StringUtil.isNotEmpty(tTaskComment.getImgIds())) {
                    List<CommentSelectOut.ImgUrl> ims = new ArrayList<>();
                    List<TFile> cimgs = fileMapper.selectByIds(tTaskComment.getImgIds());
                    if(null!=cimgs && cimgs.size()>0) {
                        cimgs.forEach(tFile -> {
                            CommentSelectOut.ImgUrl im = new CommentSelectOut.ImgUrl();
                            im.setImgUrl(tFile.getImgUrl());
                            ims.add(im);
                        });
                        commentSelectOut.setCommentImgArr(ims);
                    }
                }
                list.add(commentSelectOut);
            }
        }
        return list;
    }
}
