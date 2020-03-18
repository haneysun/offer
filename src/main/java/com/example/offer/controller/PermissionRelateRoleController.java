package com.example.offer.controller;

import com.example.offer.core.ErrorCode;
import com.example.offer.entity.Permission;
import com.example.offer.mv.PermissionTree;
import com.example.offer.mv.ResponseView;
import com.example.offer.service.IPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by 春春
 * 2019/4/26
 */
@RestController
@RequestMapping("permission/")
public class PermissionRelateRoleController extends BaseController {
    @Autowired
    public IPermissionService iPermissionService;

    @RequestMapping(value = "authorityassignment/getPermissionsAndCheckByRole", method = RequestMethod.POST)
    public List<PermissionTree> getPermissionsAndCheckById(Long roleId){
        List<PermissionTree> list=iPermissionService.getPermissionsAndCheckByRole(roleId);
        return list;
    }
    @RequestMapping(value = "public/getPermissionsRole", method = RequestMethod.GET)
    public Map<String,Map<String,Object>> getPermissionsRole(Long roleId, Long pid){
        return iPermissionService.getPermissionsByRole(roleId,pid);
    }
    /**
     * 操作角色的权限
     * @param roleId 	角色ID
     * @param ids		权限ID，以‘,’间隔
     * @return
     */
    @RequestMapping(value="authorityassignment/addPermission2Role")
    public ResponseView addPermission2Role(Long roleId, String ids){
        try{
            iPermissionService.addPermission2Role(roleId,ids);
        } catch (Exception e) {
            return ResponseView.errorsInfo(ErrorCode.updateError,"保存失败");
        }
        return ResponseView.success(null);
    }
    @RequestMapping(value="public/ids")
    public ResponseView ids(int roleId, String ids){
        return ResponseView.success(iPermissionService.selectIds(roleId));
    }
    @RequestMapping(value="findMenuPermissionByUserId")
    public ResponseView findMenuPermissionByUserId(Long userId){
        Map<String, Boolean> map = new HashMap<>();
        map.put("/index/readme",true);
        map.put("/index/personalInfo",true);
        map.put("/example/tableList",true);
        map.put("/agent/addAgent",true);
        map.put("/systemSet/getPermissionsAndCheckByRole",true);
        map.put("/agent/changeAgent",true);
        map.put("/agent/agentInfo",true);
        map.put("/agent/name",true);
        map.put("/agent/allAgent",true);
        map.put("/agentMoney/moneyRun",true);
        map.put("/agentMoney/moneyPay",true);
        map.put("/agentMoney/moneyRecord",true);
        map.put("/user/updateUserPassword",true);
        map.put("/user/addUser",true);
        map.put("/customer/customChange",true);
        map.put("/customer/customStatus",true);
        map.put("/deal/dealOrder",true);
        map.put("/deal/dealRun",true);
        map.put("/dsystem/systemRole",true);
        map.put("/dsystem/systemWork",true);
        map.put("/movie/newMovie",true);
        map.put("/movie/movieSearch",true);
        map.put("/errorpage/401",false);
        map.put("/errorpage/404",false);
        map.put("/example/form",true);
        map.put("/example/tinymce",true);
        map.put("/example/mixin",true);
        map.put("/example/31",false);
        map.put("/studentsManage/studentAdd",true);
        map.put("/studentsManage/studentUpdate",true);
        map.put("/studentsManage/studentList",true);
        List<PermissionTree> menus=iPermissionService.findMenuPermissionByUserId(userId);
        for (Permission permission:menus){
            if(StringUtils.isNotBlank(permission.getEnglishName())) map.put(permission.getEnglishName(),true);
        }
        return ResponseView.success(map);
    }
}
