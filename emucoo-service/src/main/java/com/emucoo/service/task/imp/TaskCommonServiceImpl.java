package com.emucoo.service.task.imp;

import com.emucoo.dto.modules.task.*;
import com.emucoo.mapper.TFileMapper;
import com.emucoo.mapper.TLoopWorkMapper;
import com.emucoo.mapper.TOperateDataForWorkMapper;
import com.emucoo.mapper.TTaskMapper;
import com.emucoo.model.SysUser;
import com.emucoo.model.TFile;
import com.emucoo.model.TLoopWork;
import com.emucoo.model.TOperateDataForWork;
import com.emucoo.service.task.TaskCommonService;
import com.emucoo.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskCommonServiceImpl implements TaskCommonService {

    @Autowired
    private TTaskMapper taskMapper;

    @Autowired
    private TLoopWorkMapper loopWorkMapper;

    @Autowired
    private TOperateDataForWorkMapper operateDataForWorkMapper;

    @Autowired
    private TFileMapper fileMapper;

    @Override
    public TaskCommonDetailOut detail(TaskCommonDetailIn vo) {
        return null;
    }

    @Override
    public void submitTask(TaskCommonSubmitIn vo, SysUser user) {

    }

    @Override
    public void auditTask(TaskCommonAuditIn vo, SysUser user) {

    }

    @Override
    public void editExcImgs(ExecuteImgIn vo, SysUser user) {
        TLoopWork loopWork = loopWorkMapper.fetchOneTaskByWorkIds(vo.getWorkID(), vo.getSubID());
        TOperateDataForWork opData = operateDataForWorkMapper.fetchOneByLoopWorkId(loopWork.getId());
        if(StringUtils.isBlank(opData.getImgIds()))
            return;

        fileMapper.deleteByIds(opData.getImgIds());
//        List<Long> oldids = Arrays.asList(opData.getImgIds().split(",")).stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());
//        for(Long id : oldids) {
//            TFile file = fileMapper.selectByPrimaryKey(id);
//            String location = file.getLocation();
//            Date fileDate = file.getFileDate();
//        }

        List<ExecuteImgIn.ExecuteImg> inimgs = vo.getExecuteImgArr();
        List<TFile> newImgs = new ArrayList<>();
        for(ExecuteImgIn.ExecuteImg inimg : inimgs) {
            TFile nimg = new TFile();
            nimg.setImgUrl(inimg.getImgUrl());
            nimg.setModifyUserId(user.getId());
            nimg.setCreateTime(DateUtil.currentDate());
            nimg.setModifyTime(DateUtil.currentDate());
            nimg.setCreateUserId(user.getId());
            nimg.setIsDel(false);

            newImgs.add(nimg);
        }
        fileMapper.insertList(newImgs);
        String newIds = StringUtils.join(newImgs.stream().map(f -> f.getId().toString()).collect(Collectors.toList()), ",");
        opData.setImgIds(newIds);
        operateDataForWorkMapper.updateByPrimaryKey(opData);
    }
}
