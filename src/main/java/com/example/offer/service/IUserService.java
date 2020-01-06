package com.example.offer.service;

import com.example.offer.entity.User;
import com.example.offer.mv.UserSearch;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author 孔邹祥
 * @since 2019-04-26
 */
public interface IUserService {
    User selectUserByPhone(User user);

    /**
     * 根据id查询出用户
     *
     * @param id 用户id
     * @return 用户信息
     */
    User selectById(int id);

    /***
     * 用户手动操作Session
     * */
    User login(String loginname, String loginpass);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    int updateUser(User user);
    User getUserByName(String loginname);

    int deleteUserById(Long id);

    int addUser(User user);

    int lockUser(User user);

    int lockAgentUser(User user);

    Set<Long> findAgentUser(User user);


    int updatePassword(User user);

    String checkIsStringArray(String areas);

    int queryLoginname(User user);


    User getUesrList(User user);

    List<User> findUserList(User user);

    PageInfo<Map<String, Object>> findUserList(User user, int page, int rows);

    PageInfo<Map<String, Object>> getUserAndRelateRoles(UserSearch userSearch);
}
