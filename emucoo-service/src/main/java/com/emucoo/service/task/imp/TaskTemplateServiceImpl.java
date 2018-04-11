package com.emucoo.service.task.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.task.*;
import com.emucoo.mapper.*;
import com.emucoo.model.*;
import com.emucoo.service.task.TaskTemplateService;

import com.emucoo.utils.DESUtil;
import com.emucoo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LXC
 * @date 2017/5/10
 */
@Service
public class TaskTemplateServiceImpl extends BaseServiceImpl<TaskTemplate> implements TaskTemplateService {
  
  @Resource
  private TaskTemplateMapper taskTemplateMapper;

  @Resource
  private ImgAppendMapper imgAppendMapper;

  @Resource
  private TaskUnionUserListMapper taskUnionUserListMapper;

  @Resource
  private DataAppendMapper dataAppendMapper;

  @Resource
  private LoopWorkMapper loopWorkMapper;

    @Resource
    private WorkImgAppendMapper workImgAppendMapper;



    @Override
    public List<ImgAppend> findImgsByTempletId(long task_templet_id) {
        return imgAppendMapper.findImgs(task_templet_id, 1);
    }

    @Override
    public DataAppend fetchDataAppend(Long id) {
        return dataAppendMapper.findDataAppendByTempletId(id);
    }
}
