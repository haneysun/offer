package com.example.offer.service;

import com.example.offer.entity.Role;
import com.example.offer.entity.User;
import com.example.offer.mv.RoleAndCheck;
import com.example.offer.mv.RoleSearch;
import com.example.offer.mv.RoleView;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author 孔邹祥
 * @since 2019-04-26
 */
public interface IRoleService {

    Set<String> findRoleByUserId(Long userId);

    List<Role> findAllRoles();

    int insertRole(RoleView role);

    PageInfo<Map<String, Object>> getRoleAndRelatePermissions(RoleSearch roleSearch);

    List<Map<String,String>> getMenusAndUrlPermissionsByRoleId(Long roleId);


    List<RoleAndCheck> getRolesAndCheckByUser(User member);


    PageInfo<Map<String, Object>> getRoles(RoleSearch roleSearch);

    void deleteRoleByIds(String ids);


    void deleteByPrimaryKey(Long id);

    Map<String,Object> queryRoleAccordingId(Role role);

    int updateRole(RoleView role);
}
