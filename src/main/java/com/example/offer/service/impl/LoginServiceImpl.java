package com.example.offer.service.impl;

import com.example.offer.dao.UserMapper;
import com.example.offer.entity.User;
import com.example.offer.service.ILoginService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginServiceImpl implements ILoginService {

    protected Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    @Resource
    private UserMapper userMapper;

    public User loginfill(User User) {
        return userMapper.loginfill(User);
    }

    public User findAction(User User) {
        return userMapper.findAction(User);
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public List<User> findAllUser() {
        return userMapper.selectAllUser();
    }


    public void initAllPwd(List<User> Users) {
        for (User User : Users) {
            //非法身份证号码默认密码设为：123456
            String newPwd = "123456";
            if (StringUtils.isNotBlank(User.getSfId()) && User.getSfId().length() == 18) {
                newPwd = User.getSfId().substring(12);
            }
            User.setLoginPass(newPwd);
            changePass(User);//加密
            userMapper.updatePassword(User);
            logger.info("更新用户密码成功！id={}",User.getId());
        }
    }

    private void changePass(User user) {
        DefaultHashService hashService = new DefaultHashService();
        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes(user.getLoginPass()))
                .setSalt(null).setIterations(1).build();
        String hex = hashService.computeHash(request).toHex();
        user.setLoginPass(hex);
    }
}
