package com.zj.zs.controller;

import com.zj.zs.domain.Result;
import com.zj.zs.domain.dto.article.TagDto;
import com.zj.zs.domain.dto.request.ZsSearchReqDto;
import com.zj.zs.service.ZsTagService;
import com.zj.zs.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ZsTagController
 * @Author zj
 * @Description
 * @Date 2024/4/15 08:21
 * @Version v1.0
 **/
@RestController
@RequestMapping("/api/tag")
@AllArgsConstructor
@Slf4j
public class ZsTagController {

    private ZsTagService zsTagService;

    @PostMapping("/list")
    public Result<List<TagDto>> listAll(@RequestBody(required = false) ZsSearchReqDto request) {
        log.info("ZsTagController######listAll: request={}", JsonUtils.toString(request));
        List<TagDto> result = zsTagService.listAll(request);
        return Result.ok(result);
    }
}
