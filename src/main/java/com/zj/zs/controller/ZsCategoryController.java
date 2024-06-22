package com.zj.zs.controller;

import com.zj.zs.domain.Result;
import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;
import com.zj.zs.service.ZsCategoryService;
import com.zj.zs.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ZsCategoyController
 * @Author zj
 * @Description
 * @Date 2024/4/14 20:24
 * @Version v1.0
 **/
@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@Slf4j
public class ZsCategoryController {

    private ZsCategoryService zsCategoryService;

    @PostMapping("/list")
    public Result<List<CategoryDto>> listAll(@RequestBody(required = false) ZsSearchReqDto request) {
        log.info("ZsCategoryController######listAll: request={}", JsonUtils.toString(request));
        List<CategoryDto> result = zsCategoryService.listAll(request);
        return Result.ok(result);
    }
}
