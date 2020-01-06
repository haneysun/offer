package com.example.offer.shiro;


import com.example.offer.configurer.ApplicationContextConfigurer;
import com.example.offer.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.mgt.SecurityManager;

import java.io.Serializable;
import java.util.*;


/**
 * 令牌管理类
 */
@Slf4j
public class TokenManager {
    //public static final String authorizationCache="authorizationCache";
    //public static final String authenticationCache="authenticationCache";
    //public static final String activeSessionCache="activeSessionCache";
    //用户登录管理
    private static SecurityManager securityManager = SecurityUtils.getSecurityManager();
    public static final SampleRealm realm = ApplicationContextConfigurer.getBean("sampleRealm", SampleRealm.class);
    private static final SessionDAO sessionDAO = ApplicationContextConfigurer.getBean("sessionDAO", SessionDAO.class);

    //当前session 存储用户信息 常量 KEY
    private static final String CURRENT_USERINFO = "currentUserInfo";

    /**
     * 获取当前登录的用户User对象
     *
     * @return
     */
    public static User getToken() {
        try {
            log.info("#获取当前用户session id {}", getSession().getId());
            User user = (User) getSession().getAttribute(CURRENT_USERINFO);
            log.info("#获取当前用户session 用户信息：{}",user);
            return user;
        } catch (Exception e) {
            log.error("#获取用户session信息异常：{}",e);
            Object currentUserInfo = getSession().getAttribute(CURRENT_USERINFO);
            BeanMap beanMap = new BeanMap(currentUserInfo);
            User token = new User();
            token.setId((Long) beanMap.get("id"));
            token.setName((String) beanMap.get("name"));
            token.setBumen((Integer) beanMap.get("bumen"));
            token.setTel((String) beanMap.get("tel"));
            token.setLoginName((String) beanMap.get("loginName"));
            token.setVal((String) beanMap.get("val"));
            token.setText((String) beanMap.get("text"));
            token.setLockup((Integer) beanMap.get("lockup"));
            return token;
        }
    }

    public static void setCurrentUserInfo(User user) {
        log.info("#登录设置用户session id {}", getSession().getId());
        getSession().setAttribute(CURRENT_USERINFO, user);
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static Long getUserId() {
        return getToken() == null ? null : getToken().getId();
    }

    /**
     * 获取当前用户的Session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 把值放入到当前登录用户的Session里
     *
     * @param key
     * @param value
     */
    public static void setVal2Session(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 从当前登录用户的Session里取值
     *
     * @param key
     * @return
     */
    public static Object getVal2Session(Object key) {

        return getSession().getAttribute(key);
    }

    /**
     * 获取验证码，获取一次后删除
     *
     * @return
     */
    public static String getVerificationCode() {
        String code = (String) getSession().getAttribute("strCode");
        getSession().removeAttribute("strCode");
        return code;
    }

    /**
     * 存储验证码
     *
     * @return
     */
    public static void setVerificationCode(String code) {
        getSession().setAttribute("strCode", code);
        log.info(getSession().getId() + ":存code:" + code);

    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return null != SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 当前用户退出登录
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 踢出当前用户名的其他在线用户
     */
    public static void logoutOther(Long userId) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            if (!getSession().getId().equals(session.getId())) {
                User user = new User();
                try {
                    user = (User) session.getAttribute(CURRENT_USERINFO);
                } catch (Exception e) {
                    Object object = session.getAttribute(CURRENT_USERINFO);
                    BeanMap beanMap = new BeanMap(object);
                    user.setId((Long) beanMap.get("id"));
                    user.setName((String) beanMap.get("name"));
                    user.setBumen((Integer) beanMap.get("bumen"));
                    user.setTel((String) beanMap.get("tel"));
                    user.setLoginName((String) beanMap.get("loginName"));
                    user.setVal((String) beanMap.get("val"));
                    user.setText((String) beanMap.get("text"));
                    user.setLockup((Integer) beanMap.get("lockup"));
                    //user.setAgentId((Integer) beanMap.get("agentId"));
                }
                if (user != null && user.getId().equals(userId)) {
                    logoutUser(session.getId());
                }
            }

        }
    }

    /**
     * 踢出指定的所有在线用户
     */
    public static void logout(Long userId) {
        System.out.println("跟踪异常......");
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        System.out.println("三生三世，跟踪结束，无异常");
        for (Session session : sessions) {
            User user = new User();
            try {
                user = (User) session.getAttribute(CURRENT_USERINFO);
            } catch (Exception e) {
                Object object = session.getAttribute(CURRENT_USERINFO);
                BeanMap beanMap = new BeanMap(object);
                user.setId((Long) beanMap.get("id"));
                user.setName((String) beanMap.get("name"));
                user.setBumen((Integer) beanMap.get("bumen"));
                user.setTel((String) beanMap.get("tel"));
                user.setLoginName((String) beanMap.get("loginName"));
                user.setVal((String) beanMap.get("val"));
                user.setText((String) beanMap.get("text"));
                user.setLockup((Integer) beanMap.get("lockup"));
                //user.setAgentId((Integer) beanMap.get("agentId"));
                log.error("#踢出所有在线用户的发生异常：{}",e);
            }
            if (user != null && user.getId().equals(userId)) {
                logoutUser(session.getId());
            }
        }
    }

    public static void logoutUser(Serializable sessionId) {
        Subject.Builder builder = new Subject.Builder(securityManager);
        builder.sessionId(sessionId);
        Subject subject = builder.buildSubject();
        if (null != subject) {
            try {
                subject.logout();
            } catch (SessionException e) {
                log.error("userLogout;", e);
            }
        }
    }

    /**
     * 登录
     *
     * @param user
     * @param reUserMe
     * @return
     */
    public static void login(User user, Boolean reUserMe) {
        ShiroToken token = new ShiroToken(user.getLoginName(), user.getLoginPass());
        log.info("# token manager login token:{},loginname:{}" , token , user.getLoginName());
        token.setRememberMe(reUserMe);
        SecurityUtils.getSubject().login(token);
    }


    /**
     * 清空当前用户权限信息。
     * 目的：为了在判断权限的时候，再次会再次 <code>doGetAuthorizationInfo(...)  </code>方法。
     * ps：	当然你可以手动调用  <code> doGetAuthorizationInfo(...)  </code>方法。
     * 这里只是说明下这个逻辑，当你清空了权限，<code> doGetAuthorizationInfo(...)  </code>就会被再次调用。
     */
    public static void clearNowUserAuth() {
        /**
         * 这里需要获取到shrio.xml 配置文件中，对Realm的实例化对象。才能调用到 Realm 父类的方法。
         */
        /**
         * 获取当前系统的Realm的实例化对象，方法一（通过 @link org.apache.shiro.web.mgt.DefaultWebSecurityManager 或者它的实现子类的{Collection<Realm> getRealms()}方法获取）。
         * 获取到的时候是一个集合。Collection<Realm>
         RealmSecurityManager securityManager =
         (RealmSecurityManager) RsaUtils.getSecurityManager();
         SampleRealm realm = (SampleRealm)securityManager.getRealms().iterator().next();
         */
        /**
         * 方法二、通过ApplicationContext 从Spring容器里获取实列化对象。
         */
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        realm.clearCachedAuthorizationInfo(principalCollection);

    }

    /**
     * 根据UserIds 	清空权限信息。
     *
     * @param userIds 用户ID
     */
    public static void clearUserAuthByUserId(Long... userIds) {

        if (null == userIds || userIds.length == 0) {
            return;
        }
        List<SimplePrincipalCollection> result = getSimplePrincipalCollectionByUserId(userIds);

        for (SimplePrincipalCollection simplePrincipalCollection : result) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }

    /**
     * 方法重载
     *
     * @param userIds
     */
    public static void clearUserAuthByUserId(List<Long> userIds) {
        if (null == userIds || userIds.size() == 0) {
            return;
        }
        clearUserAuthByUserId(userIds.toArray(new Long[0]));
    }

    /**
     * 根据UserIds 	清空用户信息。
     *
     * @param userIds 用户ID
     */
    public static void clearUserInfoByUserId(Long... userIds) {
        if (null == userIds || userIds.length == 0) {
            return;
        }
        List<SimplePrincipalCollection> result = getSimplePrincipalCollectionByUserId(userIds);
        for (SimplePrincipalCollection simplePrincipalCollection : result) {
            realm.clearCachedAuthenticationInfo(simplePrincipalCollection);
        }
    }

    /**
     * 方法重载
     *
     * @param userIds
     */
    public static void clearUserInfoByUserId(List<Long> userIds) {
        if (null == userIds || userIds.size() == 0) {
            return;
        }
        clearUserInfoByUserId(userIds.toArray(new Long[0]));
    }

    /**
     * 根据ID查询 SimplePrincipalCollection
     *
     * @param userIds 用户ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Long... userIds) {
        //把userIds 转成Set，好判断
        Set<Long> idset = new HashSet(Arrays.asList(userIds));
        //获取所有session
        Collection<Session> sessions = sessionDAO.getActiveSessions();

        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session : sessions) {
            //获取SimplePrincipalCollection
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (null != obj && obj instanceof SimplePrincipalCollection) {
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if (null != obj) {
                    User user = new User();
                    try {
                        user = (User) session.getAttribute(CURRENT_USERINFO);
                    } catch (Exception e) {
                        Object object = session.getAttribute(CURRENT_USERINFO);
                        BeanMap beanMap = new BeanMap(object);
                        user.setId((Long) beanMap.get("id"));
                        user.setName((String) beanMap.get("name"));
                        user.setBumen((Integer) beanMap.get("bumen"));
                        user.setTel((String) beanMap.get("tel"));
                        user.setLoginName((String) beanMap.get("loginName"));
                        user.setVal((String) beanMap.get("val"));
                        user.setText((String) beanMap.get("text"));
                        user.setLockup((Integer) beanMap.get("lockup"));
                        //user.setAgentId((Integer) beanMap.get("agentId"));
                        log.error("#发生异常 {}",e);
                    }
                    if (user != null && idset.contains(user.getId())) {
                        list.add(spc);
                    }
                }
            }
        }
        return list;
    }


    /**
     * 根据userId获取用户信息
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<User> getUserInfoByUserId(Long userId) {
        List<User> users = new ArrayList<User>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            User user = new User();
            try {
                user = (User) session.getAttribute(CURRENT_USERINFO);
            } catch (Exception e) {
                Object object = session.getAttribute(CURRENT_USERINFO);
                BeanMap beanMap = new BeanMap(object);
                user.setId((Long) beanMap.get("id"));
                user.setName((String) beanMap.get("name"));
                user.setBumen((Integer) beanMap.get("bumen"));
                user.setTel((String) beanMap.get("tel"));
                user.setLoginName((String) beanMap.get("loginName"));
                user.setVal((String) beanMap.get("val"));
                user.setText((String) beanMap.get("text"));
                user.setLockup((Integer) beanMap.get("lockup"));
               // user.setAgentId((Integer) beanMap.get("agentId"));
                log.error("#发生异常 {}",e);
            }
            if (user == null) {
                log.warn("currentUserInfo is null!sessionid={}", session.getId());
                continue;
            }
            if (user != null & user.getId().equals(userId)) {
                users.add(user);
            }
        }
        return users;
    }

}
