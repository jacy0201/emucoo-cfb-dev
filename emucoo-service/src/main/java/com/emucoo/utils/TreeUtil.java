package com.emucoo.utils;

import com.emucoo.dto.modules.sys.TreeNodeVo;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.emucoo.dto.base.ISystem.SYS_DEPT_ROOT;
import static com.emucoo.dto.base.ISystem.SYS_DEPT_ROOT_NAME;

/**
 * @author: river
 * @description:
 * @date: created at 2018/2/2 13:41
 * @modified by:
 */
public class TreeUtil {

    public static TreeNodeVo getTreeRoot(List<TreeNode> treeNodes){
        if(CollectionUtils.isEmpty (treeNodes)){
            return new TreeNodeVo (SYS_DEPT_ROOT,SYS_DEPT_ROOT_NAME);
        }
        Map<Long,TreeNodeVo> treeMap = treeNodes.parallelStream ()
                .collect (Collectors.toMap (TreeNode::getId, treeNode ->{
                    TreeNodeVo treeNodeVo = new TreeNodeVo();
                    treeNodeVo.setId (treeNode.getId ());
                    treeNodeVo.setPid (treeNode.getParentId ());
                    treeNodeVo.setName (treeNode.getName ());
                    return treeNodeVo;
                }));
        treeMap.put(SYS_DEPT_ROOT,new TreeNodeVo ());
        treeMap.forEach ((k,v)->{
            Long pid = v.getPid ();
            TreeNodeVo pNode = treeMap.get (pid);
            if(pNode == null) {
                return;
            }
            pNode.addNode (v);
        });
        return treeMap.get (SYS_DEPT_ROOT);
    }
}
