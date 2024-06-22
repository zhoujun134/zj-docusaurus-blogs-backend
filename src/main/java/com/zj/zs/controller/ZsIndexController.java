package com.zj.zs.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.zj.zs.domain.Result;
import com.zj.zs.domain.dto.home.HomeInfoDto;
import com.zj.zs.domain.dto.page.ZsFriendsDto;
import com.zj.zs.domain.dto.request.LoginUserReqDto;
import com.zj.zs.domain.dto.request.RegisterUserInfoReqDto;
import com.zj.zs.service.UserService;
import com.zj.zs.service.ZsHomeService;
import com.zj.zs.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class ZsIndexController {

    private UserService userService;

    private ZsHomeService  zsHomeService;

    @GetMapping("/index")
    @ApiOperation("主页相关信息")
    public Result<HomeInfoDto> index() {
        return Result.ok(new HomeInfoDto(StpUtil.isLogin()));
    }

    @GetMapping("/friends")
    @ApiOperation("友链列表")
    public Result<Map<String, List<ZsFriendsDto>>> friends() {
        Map<String, List<ZsFriendsDto>> result = zsHomeService.getFriendsList();
        return Result.ok(result);
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<SaTokenInfo> login(@RequestBody @Valid LoginUserReqDto request) {
        log.info("ZsIndexController######login: request={}", JsonUtils.toString(request));
        final SaTokenInfo result = userService.login(request);
        return Result.ok(result);
    }

    @GetMapping("/logout")
    @ApiOperation("登出")
    public Result<String> logout() {
        userService.logout();
        return Result.ok("");
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Result<Boolean> register(@RequestBody @Valid RegisterUserInfoReqDto request) {
        log.info("ZsIndexController######register: before request={}", JsonUtils.toString(request));
        final Boolean result = userService.register(request);
        log.info("ZsIndexController######register: after request={} result={}", JsonUtils.toString(request), result);
        return Result.ok(result);
    }

}
