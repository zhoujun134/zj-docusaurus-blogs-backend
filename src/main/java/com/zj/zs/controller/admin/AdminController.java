package com.zj.zs.controller.admin;

import com.zj.zs.domain.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdminController
 * @Author zj
 * @Description 管理类接口
 * @Date 2024/3/10 12:06
 * @Version v1.0
 **/
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/index")
    public Result<String> index() {
        return Result.ok("admin 系统启用该接口！");
    }

}
