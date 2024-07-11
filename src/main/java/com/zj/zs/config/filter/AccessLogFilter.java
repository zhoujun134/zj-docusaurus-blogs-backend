package com.zj.zs.config.filter;

import com.google.common.collect.Maps;
import com.zj.zs.constants.GlobalConstants;
import com.zj.zs.converter.AccessConverter;
import com.zj.zs.dao.DictionaryManager;
import com.zj.zs.domain.dto.config.AccessConfigDto;
import com.zj.zs.domain.dto.config.AccessIpConfigDto;
import com.zj.zs.domain.dto.monitor.AccessLogDTO;
import com.zj.zs.service.cache.CacheService;
import com.zj.zs.service.monitor.AccessLogService;
import com.zj.zs.utils.JsonUtils;
import com.zj.zs.utils.QQSendEmailService;
import com.zj.zs.utils.RequestUtils;
import com.zj.zs.utils.exception.BusinessException;
import com.zj.zs.utils.exception.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import static com.zj.zs.constants.GlobalConstants.*;

/**
 * @author zhoujun09@kuaishou.com
 * Created on 2024-04-25
 */@Slf4j
@AllArgsConstructor
@WebFilter
@Order(2)
public class AccessLogFilter implements Filter {

    private CacheService cacheService;

    private AccessLogService accessLogService;

    private AccessConverter converter;

    private DictionaryManager dictionaryManager;

    private QQSendEmailService qqSendEmailService;

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
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");
        saveLog(realIpAddr, requestURI, request, parameterMap, requestBody, userAgent, referer, headerMap);
        // 校验是否在黑名单中
        boolean isBlockAccess = checkIsCanAccess(realIpAddr, requestURI, request.getMethod(), requestBody, userAgent, referer);
        if (isBlockAccess) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendError(HttpStatus.FORBIDDEN.value(), ResultCode.NO_OPERATOR_AUTH.getMessage());
            return;
        }
        log.info("AccessLogFilter##doFilter: requestURI={}, remoteAddr={} sessionId={}, localAddr={}, parameterMap={}, requestBody={}",
                requestURI, realIpAddr, requestedSessionId, localAddr, JsonUtils.toString(parameterMap), requestBody);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void saveLog(String realIpAddr, String requestURI, HttpServletRequest request,
                         Map<String, String[]> parameterMap, String requestBody, String userAgent, String referer,
                         Map<String, String> headerMap) {
        AccessLogDTO accessLogDTO = converter.toDTO(realIpAddr, requestURI,
                request.getMethod(),
                JsonUtils.toString(new ZsRequestBody(parameterMap, requestBody)),
                userAgent,
                referer);
        accessLogService.saveAccessLog(accessLogDTO);
        log.info("AccessLogFilter##doFilter: log realIpAddr:{}, headerMap={},  requestURI={}, parameterMap={}, requestBody={}",
                realIpAddr, JsonUtils.toString(headerMap), requestURI, JsonUtils.toString(parameterMap), requestBody);
    }

    public boolean checkIsCanAccess(String sourceIp, String requestURI, String method,
                                    String paramMap, String userAgent, String referer) {
        final AccessConfigDto accessConfig = dictionaryManager.getAccessConfig();
        boolean isBlockAcess = false;
        if (StringUtils.isAnyBlank(sourceIp, referer)) {
            log.warn("##checkIsCanAccess: sourceIp or referer is blank，sourceI={}, requestURI={}, method={},userAgent={}, paramMap={}",
                    sourceIp, requestURI, method, userAgent, JsonUtils.toString(paramMap));
            isBlockAcess = true;
        }
        if (!isBlockAcess && accessConfig.getOneLevelBlackIpList().contains(sourceIp)) {
            log.warn("##checkIsCanAccess: sourceIp 不允许访问 oneLevelBlackIpList，sourceI={}, requestURI={}, method={},userAgent={}, paramMap={}",
                    sourceIp, requestURI, method, userAgent, JsonUtils.toString(paramMap));
            isBlockAcess = true;
        }
        Map<String, AccessIpConfigDto> blackIpConfigMap = accessConfig.getBlackIpConfigMap();
        if (!isBlockAcess && blackIpConfigMap.containsKey(sourceIp)) {
            final AccessIpConfigDto accessIpConfigDto = blackIpConfigMap.get(sourceIp);
            if (accessIpConfigDto.getUrls().contains(requestURI)) {
                log.warn("##checkIsCanAccess: sourceIp getUrls 不允许访问 blackIpConfigMap，sourceIp={}, requestURI={}, method={},userAgent={}, paramMap={}",
                        sourceIp, requestURI, method, userAgent, JsonUtils.toString(paramMap));
                isBlockAcess = true;
            }
            if (!isBlockAcess && accessIpConfigDto.getUserAgents().contains(userAgent)) {
                log.warn("##checkIsCanAccess: sourceIp getUrls 不允许访问 blackIpConfigMap，sourceIp={}, requestURI={}, method={},userAgent={}, paramMap={}",
                        sourceIp, requestURI, method, userAgent, JsonUtils.toString(paramMap));
                isBlockAcess = true;
            }
            if (!isBlockAcess && accessIpConfigDto.getRefererList().contains(referer)) {
                log.warn("##checkIsCanAccess: sourceIp referer 不允许访问 blackIpConfigMap，sourceIp={}, requestURI={}, method={},userAgent={}, paramMap={}",
                        sourceIp, requestURI, method, userAgent, JsonUtils.toString(paramMap));
                isBlockAcess = true;
            }
        }
        if (isBlockAcess) {
            emailMessageNotify(sourceIp, requestURI, method, paramMap, userAgent, referer);
        }
        return isBlockAcess;
    }

    private void emailMessageNotify(String sourceIp, String requestURI, String method, String paramMap, String userAgent,
                                    String referer) {
        String subject = "Z 不殊站点的【异常IP访问被拦截通知】";
        String text = """
                【异常IP访问被拦截通知】
                来源IP：%s  \n
                请求URI：%s \n
                请求方法：%s \n
                请求参数：%s \n
                请求UA：%s \n
                请求Referer：%s
                """;
        text = String.format(text, sourceIp, requestURI, method, JsonUtils.toString(paramMap), userAgent, referer);
        qqSendEmailService.sendEmail(subject, text, GlobalConstants.emailConfigDto.getUserSenderEmail());
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
