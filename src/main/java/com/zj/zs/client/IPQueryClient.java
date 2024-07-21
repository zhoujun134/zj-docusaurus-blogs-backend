package com.zj.zs.client;

import com.zj.zs.domain.dto.ip.IPDetailDto;
import com.zj.zs.domain.dto.ip.IPQueryResultDto;
import com.zj.zs.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

/**
 * @ClassName IPQueryClient
 * @Author zj
 * @Description
 * @Date 2024/7/21 16:51
 * @Version v1.0
 **/
@Slf4j
public class IPQueryClient {

    private static final String IP_QUERY_URL = "https://searchplugin.csdn.net/api/v1/ip/get?ip=%s";

    private static final HttpClient client = HttpClient.newHttpClient();
    public static IPDetailDto queryIpInfo(String ip) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(String.format(IP_QUERY_URL, ip)))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String resString = response.body();
            IPQueryResultDto ipQueryResultDto = JsonUtils.parseObject(resString, IPQueryResultDto.class);
            if (Objects.isNull(ipQueryResultDto) || ipQueryResultDto.getCode() != 200 || Objects.isNull(ipQueryResultDto.getData())) {
                return new IPDetailDto(ip, "未查询到 ip 来源");
            }
            return ipQueryResultDto.getData();
        } catch (Exception exception) {
            log.error("IPQueryClient###queryIpInfo 发生异常了，源 ip = {}", ip, exception);
            throw new RuntimeException(exception);
        }
    }
}
