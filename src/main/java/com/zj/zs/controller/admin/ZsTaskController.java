package com.zj.zs.controller.admin;

import com.zj.zs.domain.Result;
import com.zj.zs.service.task.ZsTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @ClassName ZsTaskController
 * @Author zj
 * @Description
 * @Date 2024/4/27 13:13
 * @Version v1.0
 **/
@RestController
@RequestMapping("/api/admin/task")
@AllArgsConstructor
@Slf4j
public class ZsTaskController {
    private ZsTaskService zsTaskService;
    @GetMapping("/sync/article/abstract/all")
    public Result<String> syncAllArticleAbstract() {
        log.info("ZsTaskController######syncAllArticleAbstract: curTime={}", LocalDateTime.now());
        zsTaskService.syncArticleAbstract();
        return Result.ok();
    }

}
