package com.example.offer.controller;


import com.example.offer.core.ErrorCode;
import com.example.offer.dao.AccountMapper;
import com.example.offer.entity.User;
import com.example.offer.mv.EUDataGridResult;
import com.example.offer.mv.ResponseView;
import com.example.offer.mv.UserSearch;
import com.example.offer.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author:春春
 * create by 春春
 * 2019/4/26
 */
@RestController
@RequestMapping("userManage/")
public class UserController extends BaseController {

    @Autowired
    public IUserService iUserService;
    @Resource
    private AccountMapper accountMapper;

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseView addUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("User") User user){
        if (StringUtils.isBlank(user.getLoginPass())){
            return ResponseView.errorsInfo(ErrorCode.paramError,"请输入密码");
        }
        if(user.getLoginPass().length()<6){
            return ResponseView.errorsInfo(ErrorCode.paramError,"密码不得小于6位数");
        }
        if (StringUtils.isBlank(user.getLoginName())){
            return ResponseView.errorsInfo(ErrorCode.paramError,"请输入登录账号");
        }
        if (StringUtils.isBlank(user.getName())){
            return ResponseView.errorsInfo(ErrorCode.paramError,"请输入姓名");
        }
        if (user.getBumen()==null){
            return ResponseView.errorsInfo(ErrorCode.paramError,"请选择用户所属角色");
        }
        int result =iUserService.queryLoginname(user);
        if(result!=0){
            return ResponseView.errorsInfo(ErrorCode.AccountExists,"登录账号已经存在");
        }
        user.setType(user.getType());
        user.setText("管理员通过用户管理添加的账号");
        int addUser=iUserService.addUser(user);
        return ResponseView.success();
    }
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public ResponseView updateUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("User") User user){
        if(user.getLoginPass()!=null&&user.getLoginPass().length()<6){
            return ResponseView.errorsInfo(ErrorCode.paramError,"密码不得小于6位数");
        }
        try{
            int result=iUserService.updateUser(user);
            if(result<=0){
                return ResponseView.errorsInfo(ErrorCode.updateError,"更新失败");
            }
        }catch (NumberFormatException e){
            return ResponseView.errorsInfo(ErrorCode.paramError,"参数错误");
        }
        //TokenManager.logout(user.getId());
        return ResponseView.success();
    }
    @RequestMapping(value = "getUserAndRelateRoles")
    public EUDataGridResult getRoles(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("UserSearch") UserSearch userSearch){
        PageInfo<Map<String, Object>> result = iUserService.getUserAndRelateRoles(userSearch);
        return ResponseView.successForPage(result);
    }
}
