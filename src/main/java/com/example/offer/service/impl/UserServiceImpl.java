package com.example.offer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.offer.dao.UserMapper;
import com.example.offer.entity.User;
import com.example.offer.mv.UserSearch;
import com.example.offer.service.IUserService;
import com.example.offer.shiro.TokenManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author 孔邹祥
 * @since 2019-04-26
 */
@Service("userServiceI")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectUserByPhone(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tel",user.getLoginName());
        user = userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 根据id查询出用户
     *
     * @param id 用户id
     * @return 用户信息
     * @author 孔邹祥
     */
    @Override
    public User selectById(int id) {
        return userMapper.selectById(id);
    }

    /***
     * 用户手动操作Session
     * */
    @Override
    public User login(String loginname, String loginpass) {
        User map = new User();
        map.setLoginName(loginname);
        map.setLoginPass(loginpass);
        changePass(map);
        User user = userMapper.loginfill(map);
        return user;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {
        if (user.getLoginPass() != null && user.getLoginPass() != "") {
            changePass(user);
        }
        return userMapper.updateUser(user);
    }
    @Override
    public User getUserByName(String loginname) {
        return userMapper.getUserByName(loginname);
    }

    @Override
    public int deleteUserById(Long id) {
        TokenManager.logout(id);
        return userMapper.deleteUserById(id);
    }
    @Override
    public int addUser(User user) {
        changePass(user);
        return userMapper.addUser(user);
    }
    @Override
    public int lockUser(User user) {
        return userMapper.lockUser(user);
    }
    @Override
    public int lockAgentUser(User user) {
        return userMapper.lockAgentUser(user);
    }
    @Override
    public Set<Long> findAgentUser(User user) {
        return userMapper.findAgentUser(user);
    }

    private void changePass(User user) {
        DefaultHashService hashService = new DefaultHashService();
        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes(user.getLoginPass()))
                .setSalt(null).setIterations(1).build();
        String hex = hashService.computeHash(request).toHex();
        user.setLoginPass(hex);
    }
    @Override
    public int updatePassword(User user) {
        changePass(user);
        return userMapper.updatePassword(user);
    }
    @Override
    public String checkIsStringArray(String areas) throws NumberFormatException {
        if (!StringUtils.isEmpty(areas)) {
            String[] areaa = areas.split(",");
            String temp = ",";
            for (String area : areaa) {
                if (!StringUtils.isEmpty(area)) {
                    Long areaId = Long.parseLong(area);
                    temp = temp + area + ",";
                }
            }
            return temp;
        }
        return null;
    }

    /**
     * 新增用户，判断登录账号是否存在
     */
    @Override
    public int queryLoginname(User user) {
        return userMapper.queryLoginname(user);
    }

    /**
     * 查询某个用户信息
     *
     * @param user User对象
     * @return 用户信息
     */
    @Override
    public User getUesrList(User user) {
        User u = userMapper.getUserList(user);
        if (u != null) {
            return u;
        } else {
            return null;
        }
    }
    @Override
    public List<User> findUserList(User user) {
        return userMapper.findUserList(user);
    }
    @Override
    public PageInfo<Map<String, Object>> findUserList(User user, int page, int rows) {
        PageHelper.startPage(page, rows); //分页相关
        Map map = new HashMap();
        map.put("id", user.getId());
        map.put("mid", user.getMid());
        List<Map<String, Object>> list = userMapper.findUserListMap(map);//查询数据
        return new PageInfo<Map<String, Object>>(list);
    }
    @Override
    public PageInfo<Map<String, Object>> getUserAndRelateRoles(UserSearch userSearch) {
        PageHelper.startPage(userSearch.getPage(), userSearch.getRows()); //分页相关
        List<Map<String, Object>> list = userMapper.findUserAndRoles(userSearch);//查询数
        return new PageInfo<Map<String, Object>>(list);
    }

}
