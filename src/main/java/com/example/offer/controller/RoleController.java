package com.example.offer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.offer.core.ErrorCode;
import com.example.offer.entity.Role;
import com.example.offer.entity.User;
import com.example.offer.mv.*;
import com.example.offer.service.IRoleService;
import com.example.offer.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * create by 春春
 * 2019/4/26
 */
@RestController
@RequestMapping("role/")
public class RoleController extends BaseController {
    @Autowired
    public IRoleService iRoleService;
    @Autowired
    public IUserService iUserService;

    @RequestMapping(value = "department/deleteRoles", method = RequestMethod.GET)
    public ResponseView deleteRoles(String  ids){
        iRoleService.deleteRoleByIds(ids);
        return ResponseView.success(null);
     }

    @RequestMapping(value = "department/getRoles")
    public EUDataGridResult getRoles(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("RoleSearch") RoleSearch roleSearch){
        PageInfo<Map<String, Object>> result = iRoleService.getRoles(roleSearch);
        return ResponseView.successForPage(result);
    }
    /**
     * 权限角色
     * @param role
     * @return
     */
    @RequestMapping(value="department/addRole",method=RequestMethod.POST)
    @ResponseBody
    public ResponseView addRole(RoleView role){
        try {
            if (StringUtils.isBlank(role.getRoleid())){
                iRoleService.insertRole(role);
            }else{
                iRoleService.updateRole(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseView.errorsInfo(ErrorCode.addError,"保存失败");
        }
        return ResponseView.success(null);
    }
    /**
     * 权限角色
     * @param role
     * @return
     */
    @RequestMapping(value = "department/queryRoleAccordingId")
    public ResponseView queryRoleAccordingId(Role role){
        String jsonObject = null;
        JSONObject jsonObject1 = null;
        try {
            Map<String, Object> map = iRoleService.queryRoleAccordingId(role);
            jsonObject = JSON.toJSONString(map);
            jsonObject1 = JSON.parseObject(jsonObject);
            if (jsonObject1==null){
                return ResponseView.errorsInfo(ErrorCode.queryCode,"查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseView.errorsInfo(ErrorCode.addError,"查询失败");
        }
        return ResponseView.success(jsonObject1);
    }

    @RequestMapping(value = "authorityassignment/getRoleAndRelatePermissions")
    @ResponseBody
    public EUDataGridResult getRoleAndRelatePermissions(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("RoleSearch") RoleSearch roleSearch){
        PageInfo<Map<String, Object>> result = iRoleService.getRoleAndRelatePermissions(roleSearch);
        return ResponseView.successForPage(result);
    }

    @RequestMapping(value="authorityassignment/getMenusAndUrlPermissionsByRoleId",method=RequestMethod.POST)
    @ResponseBody
    public ResponseView getMenusAndUrlPermissionsByRoleId(Long roleId){
        return   ResponseView.success(iRoleService.getMenusAndUrlPermissionsByRoleId(roleId));
    }

    @RequestMapping(value = "getRolesAndCheckByUser", method = RequestMethod.POST)
    public List<RoleAndCheck> getRolesAndCheckByUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("User") User user){
        List<RoleAndCheck> list=iRoleService.getRolesAndCheckByUser(user);
        return list;
    }


}
