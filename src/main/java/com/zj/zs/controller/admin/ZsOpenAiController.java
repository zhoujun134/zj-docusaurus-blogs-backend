package com.zj.zs.controller.admin;

import com.zj.zs.domain.Result;
import com.zj.zs.domain.dto.openai.OpenApiResDto;
import com.zj.zs.domain.dto.request.OpenApiChatReqDto;
import com.zj.zs.service.OpenAiService;
import com.zj.zs.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ZsOpenAiController
 * @Author zj
 * @Description
 * @Date 2024/4/27 10:31
 * @Version v1.0
 **/
@RestController
@RequestMapping("/api/admin/openai")
@AllArgsConstructor
@Slf4j
public class ZsOpenAiController {

    private final OpenAiService openAiService;

    @PostMapping("/chat")
    public Result<OpenApiResDto> chat(@RequestBody(required = false) OpenApiChatReqDto request) {
        log.info("ZsTagController######listAll: request={}", JsonUtils.toString(request));
        OpenApiResDto resDto = openAiService.generateText(request.getText());
        return Result.ok(resDto);
    }
}
