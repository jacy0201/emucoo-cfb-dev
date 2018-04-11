package com.emucoo.service.task.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.emucoo.dto.modules.task.ExecuteImgIn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.WorkImgAppendMapper;
import com.emucoo.model.WorkImgAppend;
import com.emucoo.service.task.WorkImgAppendService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Transactional
@Service
public class WorkImgAppendServiceImpl extends BaseServiceImpl<WorkImgAppend> implements WorkImgAppendService {

	@Resource
	private WorkImgAppendMapper mapper;

	@Override
	public void add(WorkImgAppend workImgAppend) {
		mapper.add(workImgAppend);
	}

	@Override
	public void addList(List<WorkImgAppend> list) {
		mapper.addList(list);
	}

	@Override
	public List<WorkImgAppend> fetchTaskImgs(String workId, String subWorkId) {
		return mapper.fetchTaskImgs(workId, subWorkId);
	}

	@Override
	public void addExcImgs(ExecuteImgIn executeImgIn){
		WorkImgAppend workImgAppend=new WorkImgAppend();
		workImgAppend.setWorkID(executeImgIn.getWorkID());
		workImgAppend.setSubWorkID(executeImgIn.getSubID());
		workImgAppend.setModifyTime(new Date());
		if(null!=executeImgIn.getTaskItemID())
		   workImgAppend.setTaskItemID(Long.parseLong(executeImgIn.getTaskItemID()));
		if(null!=executeImgIn.getWorkType())
		   workImgAppend.setInputType(Integer.parseInt(executeImgIn.getWorkType()));

		List<String> imgList=new ArrayList<String>();
		for(ExecuteImgIn.ExecuteImg executeImg:executeImgIn.getExecuteImgArr()){
			imgList.add(executeImg.getImgUrl());
		}
		workImgAppend.setImgurls(StringUtils.join(imgList.toArray(), ","));
		Example example=new Example(WorkImgAppend.class);
		example.createCriteria().andEqualTo("workID",executeImgIn.getWorkID())
				                .andEqualTo("subWorkID",executeImgIn.getSubID());
		if(null!=executeImgIn.getTaskItemID()&& !"".equals(executeImgIn.getTaskItemID())){
			example=new Example(WorkImgAppend.class);
			example.createCriteria().andEqualTo("workID",executeImgIn.getWorkID())
                    .andEqualTo("subWorkID",executeImgIn.getSubID())
                    .andEqualTo("taskItemID",executeImgIn.getTaskItemID());
        }
		mapper.updateByExampleSelective(workImgAppend,example);
	}


}
