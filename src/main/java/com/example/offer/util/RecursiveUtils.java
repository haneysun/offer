package com.example.offer.util;

import com.example.offer.mv.PermissionTree;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname RecursiveUtils
 * @Description TODO
 * @Date 2019/11/20 16:59
 * @Created by MaHC
 */
public class RecursiveUtils {

    //循环查找所有父节点
    public static List<PermissionTree> buildByRecursive(List<PermissionTree> treeNodes) {
        List<PermissionTree> trees = new ArrayList<PermissionTree>();
        for (PermissionTree treeNode : treeNodes) {
            if (StringUtils.isEmpty(treeNode.getPid())) {
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    //添加所有子节点
    public static PermissionTree findChildren(PermissionTree treeNode,List<PermissionTree> treeNodes) {
        for (PermissionTree it : treeNodes) {
            if(String.valueOf(treeNode.getId()).equals(it.getPid())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<PermissionTree>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}
