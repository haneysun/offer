package com.example.offer.dao;


import com.example.offer.entity.RolePermission;
import tk.mybatis.mapper.common.Mapper;

import java.util.Set;

/**
 * create by 春春
 *  2019/4/26
 */
public interface RolePermissionMapper extends Mapper<RolePermission> {
    int deleteByRid(Long id);
    int addRolePermission(RolePermission rolePermission);
    int deleteByPid(Long id);
    Set<Integer> selectIds(int id);
}