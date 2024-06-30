package com.zj.zs.config.filter;


import cn.dev33.satoken.stp.StpUtil;
import com.zj.zs.constants.GlobalConstants;
import com.zj.zs.dao.UserManager;
import com.zj.zs.domain.ZsRequestContext;
import com.zj.zs.domain.dto.UserInfoDto;
import com.zj.zs.domain.entity.ZsUserDO;
import com.zj.zs.service.cache.CacheService;
import com.zj.zs.utils.Safes;
import com.zj.zs.utils.UUIDUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.zj.zs.constants.GlobalConstants.ZS_BLOG_SESSION_ID;

@Slf4j
@AllArgsConstructor
@WebFilter
@Order(1)
public class LoginFilter implements Filter {

    @Resource
    private UserManager userManager;

    @Resource
    private CacheService cacheService;

    @Value("#{'${zs.boot.sso.paths}'.split(',')}")
    private List<String> filterPaths;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("LoginFilter#####doFilter: before... ");
        checkAndSetSessionId(servletRequest, servletResponse);
        boolean isException = false;
        try {
            final String loginIdAsString = StpUtil.getLoginIdAsString();
            final ZsUserDO userInfo = userManager.getByUsername(loginIdAsString);
            String userName;
            Long userId;
            if (Objects.isNull(userInfo)) {
                userName = GlobalConstants.DEFAULT_USER_NAME;
                userId = -1L;
            } else {
                userName = userInfo.getUsername();
                userId = userInfo.getId();
            }
            final UserInfoDto userInfoDto = new UserInfoDto(userName, userId);
            ZsRequestContext.put(userInfoDto);
            log.info("LoginFilter#####doFilter: insert userInfo userName={}", userName);
        } catch (Exception e) {
//            log.warn("LoginFilter#####doFilter: 未获取到用户信息！");
            isException = true;
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception exception) {
            log.error("LoginFilter#####doFilter: 出现异常了: exception message={}",
                    exception.getMessage(), exception);
            throw exception;
        } finally {
            if (!isException) {
                ZsRequestContext.removeAll();
            }
        }
        log.info("LoginFilter#####doFilter: after... ");
    }

    private boolean isValidSessionId(String zsBlogSessionId) {
        if (StringUtils.isBlank(zsBlogSessionId)) {
            return false;
        }
        final String cacheKey = String.format("%s.%s", ZS_BLOG_SESSION_ID, zsBlogSessionId);
        boolean exist = cacheService.exist(cacheKey);
        log.info("##isValidSessionId: cacheKey={}, exist={}", cacheKey, exist);
        return exist;
    }

    private void checkAndSetSessionId(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestedSessionId = request.getRequestedSessionId();
        log.info("LoginFilter#####checkAndSetSessionId: before... requestedSessionId={}", requestedSessionId);
        Cookie[] cookies = request.getCookies();
        List<Cookie> cookieList = Safes.streamOf(cookies)
                .filter(cookie -> StringUtils.equals(ZS_BLOG_SESSION_ID, cookie.getName()))
                .toList();
        if (CollectionUtils.isEmpty(cookieList)) {
            log.info("LoginFilter#####checkAndSetSessionId: cookieList is empty");
            String uuid = UUIDUtils.uuid();
            String cacheKey = String.format("%s.%s", ZS_BLOG_SESSION_ID, uuid);
            log.info("##checkAndSetSessionId: cacheKey={}, uuid={}", cacheKey, uuid);
            cacheService.setExpire(cacheKey, uuid, 60 * 5);
            Cookie zsBlogSessionCookie = new Cookie(ZS_BLOG_SESSION_ID, uuid);
            zsBlogSessionCookie.setMaxAge(60 * 5);
            response.addCookie(zsBlogSessionCookie);
            return;
        }
        int index =  cookieList.size() - 1;
        if (cookieList.size() > 1) {
            for (int i = 0; i < cookieList.size() - 1; i++) {
                Cookie cookie = cookieList.get(i);
                if (Objects.isNull(cookie)) {
                    continue;
                }
                cookie.setMaxAge(0);
            }
        }
        Cookie zsBlogSessionCookie = cookieList.get(index);
        String zsBlogSessionId = Objects.isNull(zsBlogSessionCookie) ? null : zsBlogSessionCookie.getValue();

        if (StringUtils.isBlank(zsBlogSessionId) || !isValidSessionId(zsBlogSessionId)) {
            String uuid = UUIDUtils.uuid();
            String cacheKey = String.format("%s.%s", ZS_BLOG_SESSION_ID, uuid);
            log.info("##checkAndSetSessionId: cacheKey={}, uuid={}", cacheKey, uuid);
            cacheService.setExpire(cacheKey, uuid, 60 * 5);
            if (Objects.nonNull(zsBlogSessionCookie)) {
                zsBlogSessionCookie.setMaxAge(0);
            }
            Cookie newZsBlogSessionCookie = new Cookie(ZS_BLOG_SESSION_ID, uuid);
            newZsBlogSessionCookie.setPath("*");
            newZsBlogSessionCookie.setMaxAge(60 * 5);
            response.addCookie(newZsBlogSessionCookie);
        }
    }
}
