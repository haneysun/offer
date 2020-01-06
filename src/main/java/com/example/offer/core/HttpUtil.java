package com.example.offer.core;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * HTTP相关的工具类
 *
 * @author Shen, Shibang
 * @since p2p_cloud_v1.0
 */
public final class HttpUtil
{

    private HttpUtil()
    {
    }

    /**
     * 
     * 通过USER-AGENT编码文件名
     * 
     * @param request
     * @return String
     * @throws UnsupportedEncodingException
     * @since p2p_cloud_v1.0
     */
    public static String getEncodedFileName(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException
    {
        String userAgent = request.getHeader("USER-AGENT");
        String finalFileName = null;
        if (StringUtils.contains(userAgent, "MSIE"))
        { // IE浏览器
            finalFileName = URLEncoder.encode(fileName, "UTF-8");
        }
        else if (StringUtils.contains(userAgent, "Mozilla"))
        { 
            if(StringUtils.containsIgnoreCase(userAgent, "TRIDENT") || StringUtils.containsIgnoreCase(userAgent, "Edge"))  // IE11浏览器
            {
                finalFileName = URLEncoder.encode(fileName, "UTF-8");
            }
            else // google,火狐浏览器
            {
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            }
        }
        else
        {
            finalFileName = URLEncoder.encode(fileName, "UTF-8");// 其他浏览器
        }
        return finalFileName;
    }
    
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
        String requestedWith = request.getHeader("X-Requested-With");
        // ajax请求
        return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
    }
    
    /**  
     * @param string
     * @param map
     * @return String
     * @since p2p_cloud_v1.0
     */
    
    public static String generateUrl(String url, Map<String, String> map)
    {
        StringBuilder sb = new StringBuilder(url);
        Set<String> set = map.keySet();
        boolean flag = true;
        for (String key : set)
        {
            String value = map.get(key);
            if (flag)
            {
                flag = false;
                sb.append("?");
            }
            else
            {
                sb.append("&");
            }
            sb.append(key + "=" +value);
        }
        return sb.toString();
    }
}
