package com.emucoo.service.cms;

import com.github.pagehelper.PageInfo;
import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.Content;

/**
 * @author fujg
 * Created by fujg on 2017/4/19.
 */
public interface ContentService extends BaseService<Content> {

    /**
     * 分页根据新闻类型查询新闻列表
     * @param catId
     * @return
     */
    PageInfo<Content> findPageNews(Integer pageNum, Integer pageSize, Long catId);
    
    PageInfo<Content> findPage(Integer pageNum ,Integer pageSize ,Long catId, String findtitle,
            String findstartTime, String findendTime, Integer effect, String ordertype ,String ordervalue) throws Exception;
}
