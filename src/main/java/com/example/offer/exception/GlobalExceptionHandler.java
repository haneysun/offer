package com.example.offer.exception;

import com.example.offer.core.BusinessErrorEnum;
import com.example.offer.core.HttpUtil;
import com.example.offer.mv.ResponseView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author lizhigang Date: 2019/3/6 16:41
 * @description:全局处理异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseView handleCommonException(Exception e, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if(!HttpUtil.isAjaxRequest(request)){
            logger.error("500异常",e.getCause(), e);
            // 跳转页面请求重定向到500页面
            String redirectUri = request.getContextPath() + "/default500";
            String message = e.getMessage();
            redirectUri += "?message=" + URLEncoder.encode(message == null ? "" : message, "UTF-8");
            response.sendRedirect(redirectUri);
            return null;
        }

        logger.error("500异常",e.getCause(), e);
        if (e instanceof BusinessException) {
            String message = getMessage(BusinessException.class.getName(), e.getMessage());
            BusinessException bex = (BusinessException) e;
            if (bex.commonError != null) {
                return ResponseView.success(bex.getErrCode(), message);
            }
        }
        return ResponseView.success(BusinessErrorEnum.UNKNOWN_ERROR.getErrCode(), e.getMessage());
    }

    @ExceptionHandler(value = {BusinessException.class})
    @ResponseBody
    public ResponseView handleCommonException(BusinessException bex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if(!HttpUtil.isAjaxRequest(request)){
            logger.error("500异常",bex.getCause(), bex);
            // 跳转页面请求重定向到500页面
            String redirectUri = request.getContextPath() + "/default500";
            String message = bex.getErrMsg();
            redirectUri += "?message=" + URLEncoder.encode(message == null ? "" : message, "UTF-8");
            response.sendRedirect(redirectUri);
            return null;
        }
        logger.error("500异常",bex.getCause(), bex);
        if (bex.commonError != null) {
            String message = getMessage(BusinessException.class.getName(), bex.getErrMsg());
            return ResponseView.success(bex.getErrCode(), message);
        }
        return ResponseView.success(BusinessErrorEnum.UNKNOWN_ERROR.getErrCode(), bex.getErrMsg());
    }

    private String getMessage(String className, String message)
    {
        String msg = null;
        if(StringUtils.isEmpty(message)){
            return msg;
        }
        String reg = className+": (.+)";

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(message);
        if(matcher.find()) {
            msg = matcher.group(1);
        }

        return msg;
    }
}
