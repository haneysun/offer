package com.example.offer.service.impl;

import com.example.offer.dao.RoleMapper;
import com.example.offer.dao.RolePermissionMapper;
import com.example.offer.dao.UserMapper;
import com.example.offer.entity.Role;
import com.example.offer.entity.User;
import com.example.offer.mv.RoleAndCheck;
import com.example.offer.mv.RoleSearch;
import com.example.offer.mv.RoleView;
import com.example.offer.service.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author 孔邹祥
 * @since 2019-04-26
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    RolePermissionMapper rolePermissionMapper;

    @Override
    public Set<String> findRoleByUserId(Long userId) {
        return roleMapper.findRoleByUserId(userId);
    }
    @Override
    public List<Role> findAllRoles() {
        return roleMapper.findAll();
    }
    @Override
    public int insertRole(RoleView role) {
        int count=roleMapper.queryRoleORBname(role);//查询当前部门是否存在
        if (count==0){
            return roleMapper.insertRole(role);
        }else{
            return 1;
        }
    }

    /**
     *
     *
     * @param roleSearch
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> getRoleAndRelatePermissions(RoleSearch roleSearch) {
        PageHelper.startPage(roleSearch.getPage(), roleSearch.getRows()); //分页相关
        List<Map<String, Object>> list = roleMapper.findRoleAndPermission(roleSearch.getName());//查询数据
        return new PageInfo<Map<String, Object>>(list);
    }
    @Override
    public List<Map<String,String>> getMenusAndUrlPermissionsByRoleId(Long roleId){
        return roleMapper.getMenusAndUrlPermissionsByRoleId(roleId);
    }
    /**
     *
     * 获取所有角色,并把此用户的角色勾选上
     *
     * @param member
     * @return
     */
    @Override
    public List<RoleAndCheck> getRolesAndCheckByUser(User member) {
        return roleMapper.getRolesAndCheckByUser(member);
    }


    /**
     *
     *
     * @param roleSearch
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> getRoles(RoleSearch roleSearch) {
        PageHelper.startPage(roleSearch.getPage(), roleSearch.getRows()); //分页相关
        List<Map<String, Object>> list = roleMapper.findRoles(roleSearch.getName());//查询数据
        return new PageInfo<Map<String, Object>>(list);
    }

    @Override
    public void deleteRoleByIds(String ids) {
        String[] idArray = new String[]{};
        if (StringUtils.contains(ids, ",")) {
            idArray = ids.split(",");
        } else {
            idArray = new String[]{ids};
        }

        c:
        for (String idx : idArray) {
            Long id = new Long(idx);
            //"系统管理员不能删除。";
            if (new Long(1).equals(id)) {
                continue ;
            } else {
                try {
                    this.deleteByPrimaryKey(id);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void deleteByPrimaryKey(Long id) {
        roleMapper.deleteRoleById(id);
        rolePermissionMapper.deleteByRid(id);
        userMapper.clearUserRoleByRoleId(id);
    }


    /**
     * 查询部门信息
     */
    @Override
    public Map<String,Object> queryRoleAccordingId(Role role){
        Map<String,Object> map = roleMapper.queryRoleAccordingId(role);
        return map;
    }
    @Override
    public int updateRole(RoleView role) {
        return roleMapper.updateRole(role);
    }
}
