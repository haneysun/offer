package com.example.offer.dao;


import com.example.offer.entity.Role;
import com.example.offer.entity.User;
import com.example.offer.mv.RoleAndCheck;
import com.example.offer.mv.RoleView;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create by 春春
 * 2019/4/26
 */
public interface RoleMapper extends Mapper<Role> {

    Set<String> findRoleByUserId(Long id);
    Set<String> findRoleByBumenId(Long id);
    public List<Role> findAll();

    /**
     * 增加部门先查询部门是否存在
     * @param role
     * @return
     */
    public int queryRoleORBname(RoleView role);
    /**
     *
     * 插入角色
     */
    public int insertRole(RoleView role);
    /**
     * name
     * 查询
     * @param
     * @return
     */
    public List<Map<String,Object>>  findRoleAndPermission(String name);


    List<RoleAndCheck> getRolesAndCheckByUser(User user);

    public List<Map<String,Object>>  findRoles(String name);
    public void deleteRoleById(Long id);

    public List<Map<String,String>> getMenusAndUrlPermissionsByRoleId(Long roleId);

    /**
     * 查询部门信息
     */
    public Map<String,Object> queryRoleAccordingId(Role role);

    public int updateRole(RoleView role);



}