package com.example.offer.shiro.filter;

import com.example.offer.core.ErrorCode;
import com.example.offer.core.ProjectConstant;
import com.example.offer.mv.ResponseView;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * URL拦截
 */
@Slf4j
@Component
public class PermissionFilter extends AccessControlFilter {
    public PermissionFilter(){

    }
    /**
     * 判断当前url是否有操作权限
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        HttpServletRequest httpRequest = ((HttpServletRequest) request);

        HttpSession session = httpRequest.getSession();
        Object currentUserInfo = session.getAttribute("currentUserInfo");

        //获取URI
        String uri = httpRequest.getRequestURI().replace("//", "/");

        if ("/user/login".equals(uri)) {
            return Boolean.TRUE;
        }
        //判定用户是否拥有某种角色，判定用户是否拥有某种功能
        if (uri != null && (subject.hasRole(ProjectConstant.administrator) || subject.isPermitted(uri))) {
            return Boolean.TRUE;
        }
        return Boolean.TRUE;
    }

    /**
     * 如果上面的方法返回false，这进入下面这个方法，给前台返回原因
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Subject subject = getSubject(request, response);
        String responseJson;
        //表示没有登录，重定向到登录页面
        if (null == subject.getPrincipal()) {
            responseJson = ResponseView.errorsInfo(ErrorCode.unlogin, "请先登陆").asJSON();
        } else {
            responseJson = ResponseView.errorsInfo(ErrorCode.unAuth, "未授权").asJSON();
        }
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.getWriter().write(responseJson);
        return Boolean.TRUE;
    }

}
