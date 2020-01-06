package com.example.offer.shiro;


import com.example.offer.entity.User;
import com.example.offer.service.IPermissionService;
import com.example.offer.service.IRoleService;
import com.example.offer.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 相当于数据源，认证，授权都在这里操作
 */
public class SampleRealm extends AuthorizingRealm {

    @Autowired
    IUserService iUserService;
    @Autowired
    IPermissionService iPermissionService;
    @Autowired
    IRoleService iRoleService;

    public SampleRealm() {
        super();
    }


    /**
     * 认证信息，主要针对用户登录，
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {

        ShiroToken token = (ShiroToken) authcToken;
        User user = iUserService.getUserByName(token.getUsername());
        if (null == user) {
            return null;
        }
        return new SimpleAuthenticationInfo(user.getName(), user.getLoginPass(), null, getName());
    }


    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Long userId = TokenManager.getUserId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据用户ID查询角色（role），放入到Authorization里。
        Set<String> roles = iRoleService.findRoleByUserId(userId);
        info.setRoles(roles);
        //根据用户ID查询权限（permission），放入到Authorization里。
        Set<String> permissions = iPermissionService.findPermissionByUserId(userId);
        //System.out.println(permissions);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 指定principalCollection 清除
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        super.clearCachedAuthorizationInfo(principalCollection);
    }

    /**
     * 指定principalCollection 清除
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principalCollection) {
        super.clearCachedAuthenticationInfo(principalCollection);

    }

    //集群中可能会导致出现验证多过5次的现象，因为AtomicInteger只能保证单节点并发
    private Cache<String, AtomicInteger> passwordRetryCache;

    @Override
    protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
        Collection perms = this.getPermissions(info);
        if (perms != null && !perms.isEmpty()) {
            Iterator i$ = perms.iterator();

            while (i$.hasNext()) {
                Permission perm = (Permission) i$.next();
                if (perm.implies(permission)) {
                    return true;
                }

                //支持通配符
                String permStr = perm.toString();
                if (permStr.endsWith("**]")) {
                    permStr = permStr.substring(0, permStr.length() - 3);
                    if (permission.toString().startsWith(permStr)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}


