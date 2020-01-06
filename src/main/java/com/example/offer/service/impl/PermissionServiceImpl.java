package com.example.offer.service.impl;

import com.example.offer.dao.PermissionMapper;
import com.example.offer.dao.RoleMapper;
import com.example.offer.dao.RolePermissionMapper;
import com.example.offer.dao.UserMapper;
import com.example.offer.entity.Permission;
import com.example.offer.entity.RolePermission;
import com.example.offer.mv.PermissionAndCheck;
import com.example.offer.mv.PermissionTree;
import com.example.offer.service.IPermissionService;
import com.example.offer.shiro.TokenManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 权限配置表 服务实现类
 * </p>
 *
 * @author 孔邹祥
 * @since 2019-04-26
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Resource
    PermissionMapper permissionMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    RolePermissionMapper rolePermissionMapper;
    @Resource
    RoleMapper roleMapper;


    public Set<String> findPermissionByUserId(Long userId) {
        Set<String> permissionByUserId = permissionMapper.findPermissionByUserId(userId);
        Set<String> urlList=new HashSet<String>();
        for(String s:permissionByUserId){
            String[] split = s.split(";");
            urlList.addAll(Arrays.asList(split));
        }
        return urlList;
    }

    /**
     * 获取用户可访问的所有菜单
     *
     * @param userId
     * @return
     */
    public List<Permission> findMenuPermissionByUserId(Long userId) {
        Set<String> types = roleMapper.findRoleByUserId(userId);
        List<Permission> permisssionList;
        if (types.contains("admin")) {
            permisssionList = permissionMapper.findAllMenuPermission();
        } else {
            permisssionList = permissionMapper.findMenuPermissionByUserId(userId);
        }

        return permisssionList;
    }

    /**
     *
     *
     * @param permisssion
     * @return
     */
    public PageInfo<Map<String, Object>> getPermissions(Permission permisssion) {
        PageHelper.startPage(permisssion.getPage(), permisssion.getRows()); //分页相关
        List<Map<String, Object>> list = permissionMapper.getPermissions(permisssion);//查询数据
        return new PageInfo<Map<String, Object>>(list);
    }

    public void insertPermission(Permission permission) {
        String url = permission.getUrl();
        String pid = permission.getPid();
        /* if (StringUtils.isNotEmpty(url)) {
            Permission oldPermission = permissionMapper.getPermissionByUrl(url);
            //这一步表示一个权限可以在多个功能下所以，注掉表示一个权限只能在一个功能下使用，
            // 如果需要多个，这需要在每个功能下都添加这个权限
           if (oldPermission != null) {
                String oldPid = oldPermission.getPid();
                String newPid = "";
                if (StringUtils.isEmpty(oldPid)) {
                    newPid = "," + pid + ",";
                } else if (oldPid.indexOf("," + pid + ",") == -1) {
                    newPid = oldPermission.getPid() + pid + ",";
                } else {
                    return;
                }
                oldPermission.setPid(newPid);
                permissionMapper.updatePermission(oldPermission);
                return;
            }
        }*/
        if (StringUtils.isNotEmpty(pid)) {
            permission.setPid("," + pid + ",");
        }
        permissionMapper.insertPermission(permission);
    }

    public void deletePermission(Long id) {
        Permission permission = permissionMapper.getPermissionById(id);

        //删除操作
        if (StringUtils.isNotEmpty(permission.getUrl())) {
            deletePermissionAndRelate(id);
        } else {
            //删除菜单,要把此菜单下的所有引用删掉
            String tempId = "," + id + ",";
            List<Permission> childs = permissionMapper.getChidPermissions(tempId);
            if (childs.size()>0) {
                for (Permission child : childs) {
                    String childPid = child.getPid();
                    if (childPid.equals(tempId)) {
                        deletePermissionAndRelate(Long.valueOf(child.getId()));
                    } else {
                        int startIndex = childPid.indexOf(tempId);
                        String newPid = childPid.substring(0, startIndex);
                        newPid += childPid.substring(startIndex + tempId.length() - 1, childPid.length());
                        child.setPid(newPid);
                        permissionMapper.updatePermission(child);
                    }
                }

            }
            deletePermissionAndRelate(id);
        }
    }

    public void deletePermissionAndRelate(Long id) {
        rolePermissionMapper.deleteByPid(id);
        permissionMapper.deletePermissionById(id);
    }

    /**
     *
     * 获取所有权限,并把此角色的权限勾选上
     *
     * @param roleId
     * @return
     */
    public List<PermissionTree> getPermissionsAndCheckByRole(Long roleId) {
        List<PermissionTree> permissionTrees = new ArrayList<PermissionTree>();
        PermissionTree tree = new PermissionTree();
        tree.setName("树");
        List<PermissionAndCheck> permisssionList = permissionMapper.getPermissionsAndCheckByRole(roleId);
        tree.setChildren(getPermissionTreeAndChecked(permisssionList));
        permissionTrees.add(tree);


        return permissionTrees;

    }

    /**
     *
     * 获取菜单下,并把此角色的权限勾选上
     *
     * @param roleId
     * @return
     */
    public Map<String,Map<String,Object>> getPermissionsByRole(Long roleId,Long pid) {
        Set<String> types = roleMapper.findRoleByBumenId(roleId);
        Map<String,Map<String,Object>> mapMap=new HashMap<>();
        List<PermissionTree> permissionChildTreeAndChecked=new ArrayList<>();
        if (types.contains("admin")) {
            List<PermissionAndCheck> permisssionList = permissionMapper.getAllPermissionsRole(roleId, pid);
            permissionChildTreeAndChecked= getPermissionChildTreeAndChecked(permisssionList, pid);
        }else {
            List<PermissionAndCheck> permisssionList = permissionMapper.getPermissionsRole(roleId, pid);
            permissionChildTreeAndChecked = getPermissionChildTreeAndChecked(permisssionList, pid);
        }
        for(PermissionAndCheck permissionAndCheck:permissionChildTreeAndChecked){
            HashMap<String, Object> map = new HashMap<>();
            map.put("checked",permissionAndCheck.isChecked());
            map.put("id",permissionAndCheck.getId());
            mapMap.put(permissionAndCheck.getName(),map);
        }
        return mapMap;
    }

    public void addPermission2Role(Long roleId, String ids) {
        //先删除原有的。
        rolePermissionMapper.deleteByRid(roleId);
        executePermission(roleId, ids);
    }

    /**
     * 处理权限
     *
     * @param roleId
     * @param ids
     * @return
     */
    private void executePermission(Long roleId, String ids) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int count = 0;
        //如果ids,permission 的id 有值，那么就添加。没值象征着：把这个角色（roleId）所有权限取消。
        if (StringUtils.isNotBlank(ids)) {
            String[] idArray = ids.split(",");
            Set<String> set = new HashSet<String>(Arrays.asList(idArray));
            for (String pid : set) {
                if (StringUtils.isNotBlank(pid)) {
                    RolePermission entity = new RolePermission(roleId, new Long(pid));
                    count += rolePermissionMapper.addRolePermission(entity);
                }
            }
        }
        //清空角拥有色Id为：roleId 的用户权限已加载数据，让权限数据重新加载
        List<Long> userIds = userMapper.findUserIdByRoleId(roleId);
        TokenManager.clearUserAuthByUserId(userIds);

    }


    public List<Permission> getAllPermission() {
        return permissionMapper.getAllPermission();
    }

    @Override
    public List<PermissionTree> getPermissionTree() {
        List<PermissionTree> permissionTrees = new ArrayList<PermissionTree>();
        PermissionTree tree = new PermissionTree();
        tree.setName("树");
        List<Permission> permisssionList = getAllPermission();
        tree.setChildren(getPermissionTree(permisssionList));
        permissionTrees.add(tree);
        return permissionTrees;
    }


    public List<PermissionTree> getPermissionChildTree(List<Permission> permissions, Long pid) {
        List<PermissionTree> permissionTrees = new ArrayList<PermissionTree>();
        for (Permission permission : permissions) {
            if (permission.getPid() != null && permission.getPid().indexOf("," + pid + ",") > -1) {
                PermissionTree permissionTree = new PermissionTree();
                List<PermissionTree> childPermissionTrees = getPermissionChildTree(permissions, Long.valueOf(permission.getId()));
                if (childPermissionTrees.size() > 0) {
                    permissionTree.setChildren(childPermissionTrees);
                }
                permissionTree.setSerialNo(permission.getSerialNo());
                permissionTree.setName(permission.getName());
                permissionTree.setEnglishName(permission.getEnglishName());
                permissionTree.setId(permission.getId());
                permissionTree.setPid(pid + "");
                if (StringUtils.isBlank(permission.getUrl())) {
                    permissionTree.setType("1");
                } else {
                    permissionTree.setType("0");
                }
                permissionTrees.add(permissionTree);
            }

        }
        return permissionTrees;
    }

    public List<PermissionTree> getPermissionTree(List<Permission> permissions) {
        List<PermissionTree> permissionTrees = new ArrayList<PermissionTree>();
        for (Permission permission : permissions) {
            //System.out.print("---------------------"+permission.getName());
            if (StringUtils.isEmpty(permission.getPid())) {
                PermissionTree permisssionTree = new PermissionTree();
                permisssionTree.setName(permission.getName());
                permisssionTree.setEnglishName(permission.getEnglishName());
                permisssionTree.setId(permission.getId());
                permisssionTree.setSerialNo(permission.getSerialNo());
                permisssionTree.setUrl(permission.getUrl());
                permisssionTree.setChildren(getPermissionChildTree(permissions, Long.valueOf(permission.getId())));
                if (StringUtils.isBlank(permission.getUrl())) {
                    permisssionTree.setType("1");//type 1为菜单
                } else {
                    permisssionTree.setType("0");//type 0为操作URL
                }
                permissionTrees.add(permisssionTree);
            }
        }

        Collections.sort(permissionTrees, new Comparator<PermissionTree>() {
            public int compare(PermissionTree o1, PermissionTree o2) {
                return o1.getSerialNo() - o2.getSerialNo();
            }
        });

        return permissionTrees;
    }


    public List<PermissionTree> getPermissionChildTreeAndChecked(List<PermissionAndCheck> permissions, Long pid) {
        List<PermissionTree> permissionTrees = new ArrayList<PermissionTree>();
        for (PermissionAndCheck permission : permissions) {
            if (permission.getPid() != null && permission.getPid().indexOf("," + pid + ",") > -1) {
                PermissionTree permissionTree = new PermissionTree();
                List<PermissionTree> childPermissionTrees = getPermissionChildTreeAndChecked(permissions, Long.valueOf(permission.getId()));
                if (childPermissionTrees.size() > 0) {
                    permissionTree.setChildren(childPermissionTrees);
                }
                permissionTree.setName(permission.getName());
                permissionTree.setEnglishName(permission.getEnglishName());
                permissionTree.setId(permission.getId());
                permissionTree.setPid(pid + "");
                permissionTree.setMarker(permission.getMarker());
                permissionTree.setRoleId(permission.getRoleId());
                if (StringUtils.isBlank(permission.getUrl())) {
                    permissionTree.setType("1");
                } else {
                    permissionTree.setType("0");
                }
                permissionTrees.add(permissionTree);
            }

        }
        return permissionTrees;
    }

    public List<PermissionTree> getPermissionTreeAndChecked(List<PermissionAndCheck> permissions) {
        List<PermissionTree> permissionTrees = new ArrayList<PermissionTree>();
        for (PermissionAndCheck permission : permissions) {
            if (StringUtils.isEmpty(permission.getPid())) {
                PermissionTree permisssionTree = new PermissionTree();
                permisssionTree.setName(permission.getName());
                permisssionTree.setEnglishName(permission.getEnglishName());
                permisssionTree.setId(permission.getId());
                permisssionTree.setMarker(permission.getMarker());
                permisssionTree.setRoleId(permission.getRoleId());
                permisssionTree.setChildren(getPermissionChildTreeAndChecked(permissions, Long.valueOf(permission.getId())));
                if (StringUtils.isBlank(permission.getUrl())) {
                    permisssionTree.setType("1");//type 1为菜单
                } else {
                    permisssionTree.setType("0");//type 0为操作URL
                }
                permissionTrees.add(permisssionTree);
            }
        }
        return permissionTrees;
    }
    public Set<Integer> selectIds(int id){
        return rolePermissionMapper.selectIds(id);
    }
}
