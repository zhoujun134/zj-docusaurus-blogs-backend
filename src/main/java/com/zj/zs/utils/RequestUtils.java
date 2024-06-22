package com.zj.zs.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <p>
 * <h3>Web Request 工具类</h3>
 * <span> 该类使用 abstaract 修饰，防止用户创建对象。其方法一般为静态，如添加非静态方法，修改者需提供具体子类。<span>
 * <pre></pre>
 * </p>
 */
@Slf4j
public abstract class RequestUtils {


    /**
     * <p> 解析客户端的 ip 地址  </p>
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * <p>获取 request body 中的内容</p>
     * <span>该方法会抛出 IOException ，需调用者处理或者抛到更上层</span>
     */
    public static String getRequestBody(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();

        //避免程序异常退出不能关闭
        try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception exception) {
            log.error("RequestUtils##getRequestBody 发生异常啦！", exception);
        }

        return sb.toString();
    }

}
