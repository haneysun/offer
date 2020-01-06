package com.example.offer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.offer.entity.User;
import com.example.offer.mv.UserSearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create by 春春
 *  2019/4/26
 */
@Repository
public interface UserMapper extends BaseMapper<User> ,Mapper<User> {

    /**
     * 根据id查询出用户
     *
     * @param id 用户id
     * @return 用户信息
     * @author 孔邹祥
     */
    User selectById(@Param("id") int id);


    /**
     * 是否存在该用户
     *
     * @param user 手机号
     * @return 是否存在
     */
    int existsUserByMobile(User user);

    /**
     * 登录
     *
     * @param user User对象
     * @return 是否登录成功
     */
    User loginUser(User user);


    /**
     * 忘记密码
     *
     * @param user User对象
     * @return 是否重置成功
     */
    int updateUserPwdByMobile(User user);

    /**
     * 查询某个用户信息 手机号
     *
     * @param user User对象
     * @return 用户信息
     */
    User getUserList(User user);
    List<User> findUserList(User user);
    List<Map<String,Object>> findUserListMap(Map map);

    List<Map<String,Object>> findUserAndRoles(UserSearch userSearch);
    /**
     * 修改密码
     *
     * @param user User对象
     * @return 是否修改成功
     */
    int updateUserPwdById(User user);


    /**
     * 更新余额、保证金、币种、汇率
     *
     * @param user User对象
     * @return 是否更新成功
     */
    int updateUserBalanceById(User user);

    /**
     * 查询某个用户信息 id
     *
     * @param user User对象
     * @return 用户信息
     */
    User getUserListById(User user);

    /**
     *  更新用户确认的交易日日期
     * @param user User对象
     * @return 是否更新成功
     */
    int updateConfirmDateById(User user);

    /**
     *  根据用户id查找当前结算单确认日期
     * @param id
     * @return
     */
    String getConfirmDateById(Long id);

    /**
     *
     * 根据名字查询用户
     * @param loginname 用户名字
     * @return 用户信息
     */
    User getUserByName(String loginname);

    /**
     *
     * 根据用户属性查找用户
     * @param user 用户属性
     * @return 用户信息
     */
    User findAction(User user);

    /**
     *
     * 添加用户
     * @param user 用户信息
     * @return 是否添加成功
     */
    int addUser(User user);

    /**
     *
     * 删除用户
     * @param id  用户id
     * @return 是否删除成功
     */
    int deleteUserById(Long id);

    /**
     *
     * 更新用户信息
     * @param user 用户信息
     * @return 是否更新成功
     */
    int updateUser(User user);

    /**
     *
     * 锁定用户
     * @param user 用户信息
     * @return 是否锁定成功
     */
    public int lockUser(User user);
    public int lockAgentUser(User user);
    /**
     *
     *
     * @param user 用户信息
     * @return 查看锁定成功的用户id
     */
    Set<Long> findAgentUser(User user);
    /**
     *
     * 更新用户密码
     * @param user 用户信息
     * @return 是否更新成功
     */
    int updatePassword(User user);

    /**
     *
     * 查找某个角色下的所有用户
     * @param id 角色id
     * @return 用户id集合
     */
    List<Long> findUserIdByRoleId(Long id);

    /**
     *
     * 清除用户角色
     * @param id 用户id
     * @return
     */
    int clearUserRoleByRoleId(long id);

    /**
     *
     * 查找所有用户
     * @return 用户信息集合
     */
    List<User> selectAllUser();

    /**
     *新增用户，判断登录账号是否存在
     */
    int queryLoginname(User user);

    /**
     * 根据身份证号查找用户信息
     * @param sfId 身份证号
     * @return 用户信息
     */
    User selectUidBySfid(String sfId);

    /**
     *
     * 根据名字查询用户
     * @param name 用户名字
     * @return 用户信息
     */
    User loginfill(User user);
}