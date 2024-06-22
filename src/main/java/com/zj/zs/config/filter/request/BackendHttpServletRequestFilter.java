package com.zj.zs.config.filter.request;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

import cn.hutool.http.ContentType;

@WebFilter(filterName = "httpServletRequestWrapperFilter", urlPatterns = {"/*"})
public class BackendHttpServletRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper((HttpServletResponse) response);
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String contentType = httpRequest.getContentType();
            // 这里判断 content-type，对于 multipart/form-data 类型将不作处理
            if (StringUtils.isNotBlank(contentType) && contentType.contains(
                    ContentType.MULTIPART.getValue())) {
                // multipart/form-data 类型
                // spring 中使用 MultipartResolver 处理文件上传，所以这里需要将其封装往后传递
                MultipartResolver resolver = new StandardServletMultipartResolver();
                MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(httpRequest);

                chain.doFilter(multipartRequest, responseWrapper);
            } else {
                // 对于其他的情况，我们统一使用包装类，将请求流进行缓存到容器
                CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                        new CachedBodyHttpServletRequest((HttpServletRequest) request);

                chain.doFilter(cachedBodyHttpServletRequest, responseWrapper);
            }

        } finally {
            // 读取完 Response body 之后，通过这个设置回去，就可以使得接口调用者可以正常接收响应了，否则会产生空响应的情况
            // 注意要在过滤器方法的最后调用
            responseWrapper.copyBodyToResponse();

        }
    }

}
