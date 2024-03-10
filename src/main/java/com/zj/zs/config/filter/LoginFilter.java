package com.zj.zs.config.filter;


import cn.dev33.satoken.stp.StpUtil;
import com.zj.zs.constants.GlobalConstants;
import com.zj.zs.dao.UserManager;
import com.zj.zs.domain.ZsRequestContext;
import com.zj.zs.domain.dto.UserInfoDto;
import com.zj.zs.domain.entity.UserDO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class LoginFilter implements Filter {

    private UserManager userManager;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("LoginFilter#####doFilter: before... ");
        boolean isException = false;
        try {
            final String loginIdAsString = StpUtil.getLoginIdAsString();
            final UserDO userInfo = userManager.getByUsername(loginIdAsString);
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
            log.warn("LoginFilter#####doFilter: 未获取到用户信息！", e);
            isException = true;
        }
        if (!isException) {
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (Exception exception) {
                log.error("LoginFilter#####doFilter: 出现异常了: exception message={}",
                        exception.getMessage(), exception);
            } finally {
                ZsRequestContext.removeAll();
            }
        }
        log.info("LoginFilter#####doFilter: after... ");
    }
}
