package com.zj.zs.service.impl;

import com.zj.zs.domain.dto.openai.NvidiaModelReqDto;
import com.zj.zs.domain.dto.openai.NvidiaSuccessResponseChoicesEntityDto;
import com.zj.zs.domain.dto.openai.NvidiaSuccessResponseDto;
import com.zj.zs.domain.dto.openai.OpenApiResDto;
import com.zj.zs.service.OpenAiService;
import com.zj.zs.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static com.zj.zs.constants.GlobalConstants.ARTICLE_ABSTRACT_PROMOTE;
import static com.zj.zs.domain.dto.openai.NvidiaConstants.NVIDIA_MODEL_URL;

/**
 * @ClassName OpenAiServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/4/27 09:57
 * @Version v1.0
 **/
@Slf4j
@Service
public class OpenAiServiceImpl implements OpenAiService {

    public static HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    @Value("${openapi.nvidia.secretKey}")
    private String apiSecretKey;

    @Override
    public OpenApiResDto generateText(String text) {
        NvidiaModelReqDto requestBody = new NvidiaModelReqDto(text);
        String requestBodyString = JsonUtils.toString(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(NVIDIA_MODEL_URL))
                .header("Authorization", "Bearer " + apiSecretKey)
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyString))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String resBodyString = response.body();
            if (response.statusCode() == 200) {
                log.info("OpenAiServiceImpl##generateText: 请求成功: response:{}, request:{}", resBodyString, requestBodyString);
                NvidiaSuccessResponseDto nvidiaSuccessResponse = JsonUtils.parseObject(resBodyString, NvidiaSuccessResponseDto.class);
                if (Objects.nonNull(nvidiaSuccessResponse) && !CollectionUtils.isEmpty(nvidiaSuccessResponse.getChoices())) {
                    List<NvidiaSuccessResponseChoicesEntityDto> choices = nvidiaSuccessResponse.getChoices();
                    NvidiaSuccessResponseChoicesEntityDto choicesMessage = choices.get(0);
                    return new OpenApiResDto(choicesMessage.getMessage().getContent());
                }
            } else {
                log.warn("OpenAiServiceImpl##generateText: 请求异常: response:{}, request:{}", response.body(), requestBodyString);
                return new OpenApiResDto("-1", "openApi 请求异常！statusCode=" + response.statusCode(), resBodyString);
            }
        } catch (Exception exception) {
            log.error("请求异常！", exception);
            return new OpenApiResDto("-1", "openApi 错误请求异常!", exception.getMessage());
        }
        return new OpenApiResDto("-1", "openApi 未知请求错误!", "");
    }

    @Override
    public String generateAbstractText(String text) {
        String content = ARTICLE_ABSTRACT_PROMOTE + text;
        OpenApiResDto resDto = generateText(text);



        return null;
    }
}
