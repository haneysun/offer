package com.example.offer.service;

import com.example.offer.entity.Permission;
import com.example.offer.mv.PermissionAndCheck;
import com.example.offer.mv.PermissionTree;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 权限配置表 服务类
 * </p>
 *
 * @author 孔邹祥
 * @since 2019-04-26
 */
public interface IPermissionService {

    Set<String> findPermissionByUserId(Long userId);

    List<Permission> findMenuPermissionByUserId(Long userId);

    PageInfo<Map<String, Object>> getPermissions(Permission permisssion);

    void insertPermission(Permission permission);

    void deletePermission(Long id);

    void deletePermissionAndRelate(Long id);

    List<PermissionTree> getPermissionsAndCheckByRole(Long roleId);

    Map<String,Map<String,Object>> getPermissionsByRole(Long roleId, Long pid);

    void addPermission2Role(Long roleId, String ids);


    List<Permission> getAllPermission();

    List<PermissionTree> getPermissionTree();

    List<PermissionTree> getPermissionChildTree(List<Permission> permissions, Long pid);

    List<PermissionTree> getPermissionTree(List<Permission> permissions);

    List<PermissionTree> getPermissionChildTreeAndChecked(List<PermissionAndCheck> permissions, Long pid);

    List<PermissionTree> getPermissionTreeAndChecked(List<PermissionAndCheck> permissions);

    Set<Integer> selectIds(int id);
}
