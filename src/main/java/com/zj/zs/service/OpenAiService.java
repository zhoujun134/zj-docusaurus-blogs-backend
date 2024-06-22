package com.zj.zs.service;

import com.zj.zs.domain.dto.openai.OpenApiResDto;

/**
 * @ClassName OpenAiService
 * @Author zj
 * @Description
 * @Date 2024/4/27 09:57
 * @Version v1.0
 **/
public interface OpenAiService {

    OpenApiResDto generateText(String text);

    String generateAbstractText(String text);
}
