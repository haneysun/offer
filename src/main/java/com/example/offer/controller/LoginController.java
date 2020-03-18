package com.example.offer.controller;

import com.example.offer.core.ErrorCode;
import com.example.offer.entity.Permission;
import com.example.offer.entity.User;
import com.example.offer.mv.EUDataGridResult;
import com.example.offer.mv.PermissionTree;
import com.example.offer.mv.ResponseView;
import com.example.offer.mv.UserLogin;
import com.example.offer.service.ILoginService;
import com.example.offer.service.IPermissionService;
import com.example.offer.service.IUserService;
import com.example.offer.shiro.TokenManager;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:春春
 * 用户管理接口
 * @date: 2019/4/26
 */
@Slf4j
@RestController
@RequestMapping("user/")
public class LoginController extends BaseController {
    @Autowired
    public IPermissionService iPermissionService;
    @Autowired
    public ILoginService iLoginService;
    @Autowired
    public IUserService iUserService;

    /**
     * @Description: LoginController
     * @Param: [userLogin, session]
     * 登陆
     * @return: com.bjst.htlh.admin.mv.ResponseView
     * @Author: MaHC
     * @Date: 2020/1/10
     * @Time: 17:56
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseView Login(@RequestBody UserLogin userLogin, HttpSession session){

        User login = new User();
        login.setLoginPass(userLogin.getLoginpass());
        login.setLoginName(userLogin.getLoginname());
        log.info("输入的账户：{}" ,login.getLoginName());
        try {
            TokenManager.login(login, Boolean.valueOf(false));
        } catch (UnknownAccountException e) {
            return ResponseView.errorsInfo(ErrorCode.noUser,"用户名不存在");
        } catch (IncorrectCredentialsException e) {
            log.info(e.getMessage());
            log.info(userLogin.getLoginpass());
            log.info("密码错误");
            if (true) {
                return ResponseView.errorsInfo(ErrorCode.pass, "密码错误");
            }
        }catch(ExcessiveAttemptsException e){
            log.info("登陆次数过多 请10分钟在登录");
            return ResponseView.errorsInfo(ErrorCode.loginXimes,"登陆次数过多 请10分钟在登录");
        }
        User user = iUserService.getUserByName(userLogin.getLoginname());
        if(user.getLockup()==0){
            return ResponseView.errorsInfo(ErrorCode.FrozenCode,"账号已冻结！请联系管理员");
        }
        //TokenManager.logoutOther(user.getId());//单点登录

        if(user.getBumen()!=null&&user.getBumen()==1){
            user.setVal("all");
        }
        //缓存用户信息到session
        TokenManager.setCurrentUserInfo(user);

        login.setId(user.getId());
        login.setLoginName(user.getLoginName());
        login.setName(user.getName());
        session.setAttribute("loginName",user.getLoginName());
        login.setLoginPass(null);
        login.setToken("12323");
        login.setBumen(user.getBumen());
        HashMap<String, Object> hashMap = new HashMap<String, Object>(16);
        hashMap.put("userStr",login);
        log.info("登录成功");
        return ResponseView.success(hashMap,"登陆成功");
    }
    /**
     * @Description: LoginController
     * @Param: []
     * 获取登陆用户信息
     * @return: com.bjst.htlh.admin.mv.ResponseView
     * @Author: MaHC
     * @Date: 2020/1/10
     * @Time: 16:29
     */
    @RequestMapping(value = "getUser")
    public ResponseView getUser(Long id){
        if (id==null){
            return ResponseView.errorsInfo(ErrorCode.unlogin,"请先登陆");
        }
        Map<String, Boolean> map = new HashMap<>(16);
        List<PermissionTree> menus=iPermissionService.findMainMenuByUserId(id);
        return ResponseView.success(menus);
    }
    /**
     * @Description: LoginController
     * @Param: [userLogin, session]
     *登出
     * @return: com.bjst.htlh.admin.mv.ResponseView
     * @Author: luoxiaowen
     * @Date: 2018/10/10
     * @Time: 17:56
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ResponseView logout(UserLogin userLogin, HttpSession session) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        TokenManager.logout();
        return ResponseView.success(null);
    }
    /**
     * @Description: LoginController
     * @Param: [user]
     * 更新用户信息
     * @return: com.bjst.htlh.admin.mv.ResponseView
     * @Author: luoxiaowen
     * @Date: 2018/10/10
     * @Time: 17:56
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseView update(User user){
        int i = iUserService.updateUser(user);
        if(i==0){
            return ResponseView.errorsInfo(ErrorCode.listnull,"失败");
        }
        return ResponseView.success(null,"成功");
    }
    /**
     * @Description: LoginController
     * @Param: [request, response, user]
     *判断登录名是否存在支持时调用
     * @return: com.bjst.htlh.admin.mv.ResponseView
     * @Author: MaHC
     * @Date: 2020/1/10
     * @Time: 17:57
     */
    @ResponseBody
    @RequestMapping(value = "queryLoginname")
    public ResponseView queryLoginname(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("User") User user){
        int result =iUserService.queryLoginname(user);
        if(result!=0){
            return ResponseView.errorsInfo(ErrorCode.AccountExists,"登录账号已经存在");
        }
        return ResponseView.success(null);
    }
    /**
     * @Description: LoginController
     * @Param: [request, response, user]
     *新增风控用户
     * @return: com.bjst.htlh.admin.mv.ResponseView
     * @Author: luoxiaowen
     * @Date: 2018/10/10
     * @Time: 17:57
     */
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public ResponseView addUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("User") User user, String againPassWord){
        //Todo 前端权限问题解决前需要加这个判断，不然会报错
        if (null==TokenManager.getUserId()){
            return ResponseView.errorsInfo(ErrorCode.unlogin,"您的session信息系统没有找到，您暂时无法访问这个接口，您可以尝试重新登陆后，再不行请联系技术部！");
        }
        if (StringUtils.isBlank(againPassWord)|| StringUtils.isBlank(user.getLoginPass())){
            return ResponseView.errorsInfo(ErrorCode.paramError,"请输入密码");
        }
        if (!againPassWord.equals(user.getLoginPass())){
            return ResponseView.errorsInfo(ErrorCode.paramError,"两次密码不一致");
        }
        int result =iUserService.queryLoginname(user);
        if(result!=0){
            return ResponseView.errorsInfo(ErrorCode.AccountExists,"登录账号已经存在");
        }
        if(user.getBumen()==null||!(user.getBumen().equals(2)||user.getBumen().equals(7))){
            return ResponseView.errorsInfo(ErrorCode.paramError,"非法参数");
        }
        user.setText(TokenManager.getToken().getLoginName()+"添加的账号");
        user.setMid(Math.toIntExact(TokenManager.getUserId()));
        int addUser=iUserService.addUser(user);
        if(addUser<=0){
            return ResponseView.errorsInfo(ErrorCode.addError,"增加失败");
        }
        return ResponseView.success(null);
    }
    /**
     * @Description: LoginController
     * @Param: [request, response, user]
     *更新用户信息
     * @return: com.bjst.htlh.admin.mv.ResponseView
     * @Author: luoxiaowen
     * @Date: 2018/10/10
     * @Time: 17:58
     */
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public ResponseView updateUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("User") User user){
        try{
            int result=iUserService.updateUser(user);
            if(result<=0){
                return ResponseView.errorsInfo(ErrorCode.updateError,"更新失败");
            }
        }catch (NumberFormatException e){
            return ResponseView.errorsInfo(ErrorCode.paramError,"参数错误");
        }
        TokenManager.logout(user.getId());
        return ResponseView.success(null);
    }
    /**
     * @Description: LoginController
     * @Param: [request, response, user]
     * 冻结账户与解冻
     * @return: com.bjst.htlh.admin.mv.ResponseView
     * @Author: luoxiaowen
     * @Date: 2018/10/10
     * @Time: 17:58
     */
    @RequestMapping(value = "lockUser", method = RequestMethod.POST)
    public ResponseView lockUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("User") User user){
        int result= iUserService.lockUser(user);
        if(result<=0){
            return ResponseView.errorsInfo(ErrorCode.lockAndUnLockError,"锁定失败");
        }
        return ResponseView.success(null);
    }
    @PostMapping("updatePassword")
    public ResponseView updatePassword(String passWord,String newPassWord,String againPassWord) {
        //Todo 前端权限问题解决前需要加这个判断，不然会报错
        if (null==TokenManager.getUserId()){
            return ResponseView.errorsInfo(ErrorCode.unlogin,"您的session信息系统没有找到，您暂时无法访问这个接口，您可以尝试重新登陆后，再不行请联系技术部！");
        }
        User user = new User();
        if (StringUtils.isBlank(newPassWord)||StringUtils.isBlank(againPassWord)||StringUtils.isBlank(passWord)){
            return ResponseView.errorsInfo(ErrorCode.paramError,"参数错误");
        }
        if(!newPassWord.equals(againPassWord)){
            return ResponseView.errorsInfo(ErrorCode.paramError,"两次输入密码不一致");
        }
        if(newPassWord.equals(passWord)){
            return ResponseView.errorsInfo(ErrorCode.paramError,"新密码不能与旧密码一致");
        }
        if(newPassWord.length()<6){
            return ResponseView.errorsInfo(ErrorCode.paramError,"新密码不得小于6位数");
        }
        user.setId(TokenManager.getUserId());
        user.setLoginName(TokenManager.getToken().getLoginName());
        user.setLoginPass(newPassWord);
        User login = iUserService.login(user.getLoginName(),passWord);
        if (login==null) {
            return ResponseView.errorsInfo(ErrorCode.noUser,"旧密码输入错误");
        }
        int i = iUserService.updatePassword(user);
        if (i > 0) {
            return ResponseView.success();
        }
        return ResponseView.errorsInfo(ErrorCode.updateError,"操作失败");
    }

    /**
     * @Description: LoginController
     * @Param: [request, response, user]
     *风控账号列表
     * @return: com.bjst.htlh.admin.mv.ResponseView
     * @Author: luoxiaowen
     * @Date: 2018/10/16
     * @Time: 17:57
     */
    @RequestMapping(value = "findUserList", method = RequestMethod.GET)
    public EUDataGridResult findUserList(int page, int rows){
        User user = new User();
        //Todo 前端权限问题解决前需要加这个判断，不然会报错
        if (null==TokenManager.getUserId()){
            user.setMid(Math.toIntExact(10101));
        }else {
            user.setMid(Math.toIntExact(TokenManager.getUserId()));
        }
        PageInfo<Map<String, Object>> result=iUserService.findUserList(user,page,rows);
        return ResponseView.successForPage(result);
    }
    @PostMapping("updateUserPassword")
    public ResponseView updateUserPassword(String newPassWord,String againPassWord,Long id) {
        //Todo 前端权限问题解决前需要加这个判断，不然会报错
        if (null==TokenManager.getUserId()){
            return ResponseView.errorsInfo(ErrorCode.unlogin,"您的session信息系统没有找到，您暂时无法访问这个接口，您可以尝试重新登陆后，再不行请联系技术部！");
        }
        User user = new User();
        if (StringUtils.isBlank(newPassWord)||StringUtils.isBlank(againPassWord)){
            return ResponseView.errorsInfo(ErrorCode.paramError,"参数错误");
        }
        if(!newPassWord.equals(againPassWord)){
            return ResponseView.errorsInfo(ErrorCode.paramError,"两次输入密码不一致");
        }
        user.setId(id);
        user.setLoginPass(newPassWord);
        user.setMid(Math.toIntExact(TokenManager.getUserId()));
        List<User> userList = iUserService.findUserList(user);
        if (userList.size()==0) {
            return ResponseView.errorsInfo(ErrorCode.noUser,"抱歉，您无法修改该账号的密码");
        }
        int i = iUserService.updatePassword(user);
        if (i > 0) {
            TokenManager.logout(user.getId());
            return ResponseView.success();
        }
        return ResponseView.errorsInfo(ErrorCode.updateError,"操作失败");
    }
}
