package com.emucoo.service.comment.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.common.util.StringUtil;
import com.emucoo.dto.modules.comment.*;
import com.emucoo.enums.FunctionType;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.comment.CommentService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl extends BaseServiceImpl<TTaskComment> implements CommentService {
    @Resource
    private TTaskCommentMapper taskCommentMapper;
    @Resource
    private TFileMapper fileMapper;
    @Resource
    private TLoopWorkMapper tLoopWorkMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private TReportMapper tReportMapper;
    @Resource
    private TRepairWorkMapper repairWorkMapper;
    @Resource
    private TBusinessMsgMapper tBusinessMsgMapper;

    @Override
    @Transactional
    public void add(CommentAddIn vo, SysUser user) {
        TTaskComment comment = new TTaskComment();
        comment.setContent(vo.getContent());
        Integer workType=vo.getWorkType();
        comment.setUnionType(workType);
        Long unionId=null;
        List<Long> receiverIdList=new ArrayList<>();
        TBusinessMsg businessMsg = new TBusinessMsg();
        businessMsg.setUnionType(workType.byteValue());
        businessMsg.setCreateTime(new Date());
        businessMsg.setFunctionType(FunctionType.COMMENT_REMIND.getCode().byteValue());
        businessMsg.setIsRead(false);
        businessMsg.setIsSend(false);
        businessMsg.setTitle(user.getRealName()+" 发表评论:“"+vo.getContent()+"”");
        businessMsg.setSendTime(new Date());
        //如果workType 为 常规、指派、改善 、备忘 任务类型，则根据 workID 和 subID 获取任务主键ID
        if (workType == 1 || workType == 2 || workType == 3 || workType==5){
            TLoopWork tLoopWork=new TLoopWork();
            tLoopWork.setWorkId(vo.getWorkID());
            tLoopWork.setSubWorkId(vo.getSubID());
            TLoopWork loopWork= tLoopWorkMapper.selectOne(tLoopWork);
            unionId=loopWork.getId();
            receiverIdList.add(loopWork.getExcuteUserId());
        }else if(workType == 4){// workType==4 巡店，巡店的评论对象是报告
            unionId=vo.getReportID();
            //根据报告id 获取执行人id
            TReport tReport=tReportMapper.selectByPrimaryKey(unionId);
            //打表人id
            receiverIdList.add(tReport.getReporterId());
        }else if(workType == 6){//workType==6 评论， 针对评论的 回复
            unionId=vo.getCommentID();
            businessMsg.setTitle(user.getRealName()+" 发表回复:“"+vo.getContent()+"”");
            //根据评论id,查询评论人id，和任务的执行人id
            TTaskComment ttc=taskCommentMapper.selectByPrimaryKey(vo.getCommentID());
            //添加被回复人id
            receiverIdList.add(ttc.getCreateUserId());
            //添加任务执行人id;
            while (6==ttc.getUnionType()){
                ttc=taskCommentMapper.selectByPrimaryKey(ttc.getUnionId());
            }
            Integer unionType=ttc.getUnionType();
            Long uid=ttc.getUnionId();
            if (unionType == 1 || unionType == 2 || unionType == 3 || unionType==5){
                TLoopWork tlw= tLoopWorkMapper.selectByPrimaryKey(uid);
                receiverIdList.add(tlw.getExcuteUserId());
            }else if(unionType==4){
                TReport tr=tReportMapper.selectByPrimaryKey(uid);
                receiverIdList.add(tr.getReporterId());
            }else if(unionType==7){
                TRepairWork trw=repairWorkMapper.selectByPrimaryKey(uid);
                //维修人员id
                receiverIdList.add(trw.getRepairManId());
            }

        }else if(workType ==7){//workType==7 维修任务
            unionId=vo.getRepairID();
            TRepairWork tRepairWork=repairWorkMapper.selectByPrimaryKey(unionId);
            //维修人员id
            receiverIdList.add(tRepairWork.getRepairManId());
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
        //添加消息
        businessMsg.setUnionId(unionId);
        addMsg(businessMsg,receiverIdList);

    }

    private void addMsg(TBusinessMsg businessMsg,List<Long> receiverIdList) {
        List<TBusinessMsg> list=new ArrayList<>();
        for(Long receiverId:receiverIdList){
            TBusinessMsg msg=businessMsg;
            msg.setReceiverId(receiverId);
            list.add(msg);
        }
        tBusinessMsgMapper.insertList(list);
    }

    @Override
    public void delete(CommentDeleteIn vo) {
        taskCommentMapper.deleteByPrimaryKey(vo.getCommentId());
    }

    /**
     * 查询评论
     * @param vo
     * @param user
     * @return
     */
    @Override
    public CommentSelectOut selectCommentList(CommentSelectIn vo, SysUser user) {
        String workId=vo.getWorkID();
        Integer workType=vo.getWorkType();
        String  subId=vo.getSubID();
        Long  reportId=vo.getReportID();
        CommentSelectOut commentSelectOut=new CommentSelectOut();
        List<CommentSelectOut.Comment> list=null;
        Long unionId=null;
        if (workType == 1 || workType == 2 || workType == 3|| workType==5){
            TLoopWork tLoopWork=new TLoopWork();
            tLoopWork.setWorkId(workId);
            tLoopWork.setSubWorkId(subId);
            TLoopWork loopWork= tLoopWorkMapper.selectOne(tLoopWork);
            unionId=loopWork.getId();
        }else if(workType==4){
            unionId=reportId;
        }else if(workType==7){
            unionId=vo.getRepairID();
        }
        Example example=new Example(TTaskComment.class);
        example.createCriteria().andEqualTo("unionId",unionId).andEqualTo("unionType",workType)
                .andEqualTo("isDel",false).andEqualTo("isShow",true);
        example.setOrderByClause("create_time desc");
        List<TTaskComment> commentList=taskCommentMapper.selectByExample(example);
        if(null!=commentList && commentList.size()>0){
            list=commentSelectOut.getCommentList();
            for(TTaskComment tTaskComment:commentList){
                CommentSelectOut.Comment out=new CommentSelectOut.Comment();
                out.setCommentContent(tTaskComment.getContent());
                out.setCommentID(tTaskComment.getId());
                out.setCommentTime(tTaskComment.getCreateTime().getTime());
                out.setCommentUserID(tTaskComment.getUserId());
                out.setCommentUserName(tTaskComment.getUserName());
                out.setCommentHeadUrl(sysUserMapper.selectByPrimaryKey(tTaskComment.getUserId()).getHeadImgUrl());
                //查询回复数量
                Example exampleReply=new Example(TTaskComment.class);
                exampleReply.createCriteria().andEqualTo("unionId",tTaskComment.getId()).andEqualTo("isDel",false);
                List<TTaskComment> listReply=taskCommentMapper.selectByExample(exampleReply);
                int replyNum=0;
                if(null!=listReply && listReply.size()>0) replyNum=listReply.size();
                out.setReplyNum(replyNum);
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
                        out.setCommentImgArr(ims);
                    }
                }
                list.add(out);
            }
            commentSelectOut.setCommentList(list);
        }
        return commentSelectOut;
    }


    /**
     * 查询回复
     * @param vo
     * @param user
     * @return
     */
    @Override
    public ReplySelectOut selectReplyList(CommentSelectIn vo, SysUser user) {
        Long commentId=vo.getCommentID();
        ReplySelectOut replySelectOut=new ReplySelectOut();
        CommentSelectOut.Comment comment=replySelectOut.getComment();
        //查询评论对象
        TTaskComment tComment=taskCommentMapper.selectByPrimaryKey(commentId);
        comment.setCommentID(tComment.getId());
        comment.setCommentContent(tComment.getContent());
        comment.setCommentTime(tComment.getCreateTime().getTime());
        comment.setCommentUserID(tComment.getUserId());
        comment.setCommentUserName(tComment.getUserName());
        comment.setCommentHeadUrl(sysUserMapper.selectByPrimaryKey(tComment.getUserId()).getHeadImgUrl());
        //设置评论照片
        if(StringUtil.isNotEmpty(tComment.getImgIds())) {
            List<CommentSelectOut.ImgUrl> ims = new ArrayList<>();
            List<TFile> cimgs = fileMapper.selectByIds(tComment.getImgIds());
            if(null!=cimgs && cimgs.size()>0) {
                cimgs.forEach(tFile -> {
                    CommentSelectOut.ImgUrl im = new CommentSelectOut.ImgUrl();
                    im.setImgUrl(tFile.getImgUrl());
                    ims.add(im);
                });
                comment.setCommentImgArr(ims);
            }
        }
        replySelectOut.setComment(comment);
        //查询回复集合
        List<ReplySelectOut.ObjectComment> replyList=null;
        Example example=new Example(TTaskComment.class);
        example.createCriteria().andEqualTo("unionId",commentId).andEqualTo("unionType",6)
                .andEqualTo("isDel",false).andEqualTo("isShow",true);
        example.setOrderByClause("create_time desc");
        List<TTaskComment> commentList=taskCommentMapper.selectByExample(example);
        if(null!=commentList && commentList.size()>0){
            replyList=replySelectOut.getReplyList();
            for(TTaskComment tTaskComment:commentList){
                ReplySelectOut.ObjectComment objectComment=new  ReplySelectOut.ObjectComment();
                objectComment.setCommentContent(tTaskComment.getContent());
                objectComment.setCommentID(tTaskComment.getId());
                objectComment.setCommentTime(tTaskComment.getCreateTime().getTime());
                objectComment.setCommentUserID(tTaskComment.getUserId());
                objectComment.setCommentUserName(tTaskComment.getUserName());
                objectComment.setCommentHeadUrl(sysUserMapper.selectByPrimaryKey(tTaskComment.getUserId()).getHeadImgUrl());
                //设置回复照片
                if(StringUtil.isNotEmpty(tTaskComment.getImgIds())) {
                    List<ReplySelectOut.ImgUrl> ims = new ArrayList<>();
                    List<TFile> cimgs = fileMapper.selectByIds(tTaskComment.getImgIds());
                    if(null!=cimgs && cimgs.size()>0) {
                        cimgs.forEach(tFile -> {
                            ReplySelectOut.ImgUrl im = new ReplySelectOut.ImgUrl();
                            im.setImgUrl(tFile.getImgUrl());
                            ims.add(im);
                        });
                        objectComment.setCommentImgArr(ims);
                    }
                }
                replyList.add(objectComment);
            }
            replySelectOut.setReplyList(replyList);
        }
        return replySelectOut;
    }
}
