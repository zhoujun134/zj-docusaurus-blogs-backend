package com.zj.zs.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.zj.zs.domain.Result;
import com.zj.zs.domain.dto.request.LoginUserReqDTO;
import com.zj.zs.domain.dto.request.RegisterUserInfoReqDTO;
import com.zj.zs.service.UserService;
import com.zj.zs.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class ZsIndexController {

    private UserService userService;

    @GetMapping("/index")
    public Result<String> index() {
        return Result.fail("系统未启用该接口！");
    }

    @PostMapping("/login")
    public Result<SaTokenInfo> login(@RequestBody @Valid LoginUserReqDTO request) {
        log.info("ZsIndexController######login: request={}", JsonUtils.toString(request));
        final SaTokenInfo result = userService.login(request);
        return Result.ok(result);
    }

    @GetMapping("/logout")
    public Result<String> logout() {
        userService.logout();
        return Result.ok("");
    }

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody @Valid RegisterUserInfoReqDTO request) {
        log.info("ZsIndexController######register: before request={}", JsonUtils.toString(request));
        final Boolean result = userService.register(request);
        log.info("ZsIndexController######register: after request={} result={}", JsonUtils.toString(request), result);
        return Result.ok(result);
    }

}
