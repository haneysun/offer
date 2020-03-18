package com.example.offer.controller;

import com.example.offer.entity.Permission;
import com.example.offer.mv.PermissionTree;
import com.example.offer.mv.ResponseView;
import com.example.offer.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * create by 春春
 * 2020/1/10
 */
@RestController
@RequestMapping("permission/")
public class PermissionController extends BaseController {
    @Autowired
    public IPermissionService iPermissionService;

    @RequestMapping(value="jurisdiction/getPermissions",method=RequestMethod.POST)
    @ResponseBody
    public List<PermissionTree> getPermissions(){
        return iPermissionService.getPermissionTree();
    }
   /**
     * 权限添加
     * @param permission
     * @return
     */
    @RequestMapping(value="jurisdiction/addPermission")
    @ResponseBody
    public ResponseView addPermission(Permission permission){
        try {
            iPermissionService.insertPermission(permission);
        } catch (Exception e) {
            return ResponseView.errorsInfo("","保存失败");
        }
        return ResponseView.success(null);
    }
    /**
     * 权限删除
     * @param id
     * @return
     */
    @RequestMapping(value="jurisdiction/deletePermission",method=RequestMethod.GET)
    @ResponseBody
    public ResponseView deletePermission(Long id){
        try {
            iPermissionService.deletePermission(id);
        } catch (Exception e) {
            return ResponseView.errorsInfo("","删除失败");
        }
        return ResponseView.success(null);
    }
}
