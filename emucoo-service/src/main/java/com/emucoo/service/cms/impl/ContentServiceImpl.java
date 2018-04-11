package com.emucoo.service.cms.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.emucoo.common.Constant;
import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.service.cms.ContentService;
import com.emucoo.mapper.ContentMapper;
import com.emucoo.model.Content;

/**
 * @author fujg
 * Created by fujg on 2017/4/19.
 */
@Transactional
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

    @Resource
    private ContentMapper contentMapper;
    
    @Transactional(readOnly = true)
    @Override
    public PageInfo<Content> findPageNews(Integer pageNum, Integer pageSize, Long catId) {
        Content content = new Content();
        content.setContentCatId(catId);
        return super.findPageListByWhere(pageNum, pageSize, content);
    }
    
    @Transactional(readOnly = true)
    @Override
    public PageInfo<Content> findPage(Integer pageNum ,Integer pageSize ,Long catId, String findtitle,
            String findstartTime, String findendTime, Integer effect, String ordertype ,String ordervalue) throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("catId", catId);
        map.put("findtitle", findtitle);
        map.put("findstartTime", findstartTime);
        map.put("findendTime", findendTime);
        map.put("effect", effect);
        
        if(Constant.ORDER_TYPE0.equals(ordertype)){
            map.put("ordertype", " id ");
        }else if(Constant.ORDER_TYPE1.equals(ordertype)){
            map.put("ordertype", " order_no ");
        }

        if(Constant.ORDER_ASC.equals(ordervalue)){
            map.put("ordervalue" , " asc ");
        }else if(Constant.ORDER_DESC.equals(ordervalue)){
            map.put("ordervalue" , " desc ");
        }
        
        PageHelper.startPage(pageNum, pageSize,true);
        List<Content> contentlist = contentMapper.contentList(map);

        return new PageInfo<Content>(contentlist);
    }
}
