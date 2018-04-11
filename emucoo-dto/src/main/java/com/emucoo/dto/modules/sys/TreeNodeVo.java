package com.emucoo.dto.modules.sys;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author: river
 * @description:
 * @date: created at 2018/2/2 12:20
 * @modified by:
 */
@Data
public class TreeNodeVo {
    private Long id;
    private Long pid;
    private String name;
    private List<TreeNodeVo> children = Lists.newArrayList ();
    private Boolean open;

    public TreeNodeVo() {
    }

    public TreeNodeVo(Long id, String text) {
        this.id = id;
        this.name = text;
    }

    public void addNode(TreeNodeVo node)
    {
        children.add(node);
    }
}
