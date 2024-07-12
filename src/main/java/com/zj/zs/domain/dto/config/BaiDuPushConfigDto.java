package com.zj.zs.domain.dto.config;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class BaiDuPushConfigDto {
    /**
     * 一次推送的 url 数
     */
    private int onePushCount = 10;

    /**
     * 百度推送的 url MAP
     * key=域名，value=推送的url
     */
    private Map<String, String> pushUrlMap = Maps.newHashMap();

    /**
     * sitemap.xml 的配置地址
     */
    private String siteMapUrl = "";

    /**
    * sitemap.xml 的配置中的 domain 地址
    */
    private String siteMapDomain = "";
}
