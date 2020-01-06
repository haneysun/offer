package com.example.offer.dao;


import com.example.offer.entity.Permission;
import com.example.offer.mv.PermissionAndCheck;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create by 春春
 *  2019/4/26
 */
public interface PermissionMapper extends Mapper<Permission> {
    /**
     *
     * 根据用户ID获取权限的Set集合
     */
    Set<String> findPermissionByUserId(Long id);

    List<Permission> findMenuPermissionByUserId(Long id);
    List<Permission> findAllMenuPermission();
    /**
     *
     * 查询权限
     */
    public List<Map<String,Object>> getPermissions(Permission permission);

    /**
     *
     * 插入权限
     */
    public int insertPermission(Permission permission);

    /**
     *
     * 获取所有权限,并把此角色的权限勾选上
     * @param roleId
     * @return
     */
    public List<PermissionAndCheck> getPermissionsAndCheckByRole(Long roleId);
    public List<PermissionAndCheck> getPermissionsRole(@Param("roleId") Long roleId, @Param("pid") Long pid);
    public List<PermissionAndCheck> getAllPermissionsRole(@Param("roleId") Long roleId, @Param("pid") Long pid);
    public void deletePermissionById(Long id);
    public List<Permission> getAllPermission();

    public Permission getPermissionByUrl(String url);
    public Permission getPermissionById(Long id);
    public void updatePermission(Permission permission);
    public List<Permission> getChidPermissions(String id);
}