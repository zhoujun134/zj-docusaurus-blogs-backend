package com.zj.zs.config.filter;

import com.google.common.collect.Maps;
import com.zj.zs.converter.AccessConverter;
import com.zj.zs.domain.dto.monitor.AccessLogDTO;
import com.zj.zs.service.cache.CacheService;
import com.zj.zs.service.monitor.AccessLogService;
import com.zj.zs.utils.JsonUtils;
import com.zj.zs.utils.RequestUtils;
import com.zj.zs.utils.exception.BusinessException;
import com.zj.zs.utils.exception.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import static com.zj.zs.constants.GlobalConstants.ACCESS_BLACK_IP_KEY;
import static com.zj.zs.constants.GlobalConstants.ACCESS_IP_KEY;

/**
 * @author zhoujun09@kuaishou.com
 * Created on 2024-04-25
 */
@Slf4j
@AllArgsConstructor
@WebFilter
@Order(2)
public class AccessLogFilter implements Filter {

    private CacheService cacheService;

    private AccessLogService accessLogService;

    private AccessConverter converter;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 请求的路径
        String requestURI = request.getRequestURI();
        // 请求来源的 ip 地址
        String ipAddr = RequestUtils.getIpAddr(request);
        String realIpAddr = request.getHeader(ACCESS_IP_KEY);
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = Maps.newHashMap();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headerMap.put(headerName, headerValue);
        }
        String blackListString = cacheService.get(ACCESS_BLACK_IP_KEY, String.class);
        if (!StringUtils.isAnyBlank(ipAddr, blackListString) && StringUtils.contains(blackListString, ipAddr)) {
            throw new BusinessException(ResultCode.USER_NOT_PERMIT_VISITED);
        }
        String requestedSessionId = request.getRequestedSessionId();
        String localAddr = request.getLocalAddr();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestBody = RequestUtils.getRequestBody(request);
        AccessLogDTO accessLogDTO = converter.toDTO(realIpAddr, requestURI,
                request.getMethod(),
                JsonUtils.toString(new ZsRequestBody(parameterMap, requestBody)),
                request.getHeader("User-Agent"),
                request.getHeader("Referer"));
        accessLogService.saveAccessLog(accessLogDTO);
        log.info("AccessLogFilter##doFilter: log realIpAddr:{}, headerMap={},  requestURI={}, parameterMap={}, requestBody={}",
                realIpAddr, JsonUtils.toString(headerMap), requestURI, JsonUtils.toString(parameterMap), requestBody);
        log.info("AccessLogFilter##doFilter: requestURI={}, remoteAddr={} sessionId={}, localAddr={}, parameterMap={}, requestBody={}",
                requestURI, realIpAddr, requestedSessionId, localAddr, JsonUtils.toString(parameterMap), requestBody);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Data
    @NoArgsConstructor
    static class ZsRequestBody {
        private String paramMap;
        private String requestBody;

        public ZsRequestBody(Map<String, String[]> paramMap, String requestBody) {
            this.paramMap = JsonUtils.toString(paramMap);
            this.requestBody = requestBody;
        }
    }
}
