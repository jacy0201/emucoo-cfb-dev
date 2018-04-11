package com.emucoo.service.cms;

import com.emucoo.common.base.service.BaseService;
import com.emucoo.model.ContentCat;
import com.emucoo.model.TreeNode;

import java.util.List;

/**
 * @author fujg
 * Created by fujg on 2017/4/19.
 */
public interface ContentCatService extends BaseService<ContentCat> {
    /**
     * 查询新闻分类
     * @return
     */
    List<ContentCat> findListNewsCat();

    /**
     * 返回树列表
     * @return
     */
    List<TreeNode> findTreeList();
}
