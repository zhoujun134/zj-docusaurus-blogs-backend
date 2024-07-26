package com.zj.zs.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.google.common.collect.Lists;
import com.zj.zs.constants.GlobalConstants;
import com.zj.zs.dao.DictionaryManager;
import com.zj.zs.domain.dto.config.BaiDuPushConfigDto;
import com.zj.zs.domain.dto.config.BaiDuPushResDto;
import com.zj.zs.utils.DateUtils;
import com.zj.zs.utils.JsonUtils;
import com.zj.zs.utils.QQSendEmailService;
import com.zj.zs.utils.Safes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class HttpUtilsService {
    @Resource
    private DictionaryManager dictionaryManager;
    @Resource
    private QQSendEmailService qqSendEmailService;

    public List<String> getSiteUrlList(String sitemapUrl) {
        if (StringUtils.isBlank(sitemapUrl)) {
            return Collections.emptyList();
        }
        List<String> urlList = Lists.newArrayList();
        try {
            // 创建文档构建器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 使用工厂创建文档构建器
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 解析 XML 文件
            Document document = builder.parse(sitemapUrl);
            // 标准化文档结构
            document.getDocumentElement().normalize();
            // 获取 urlset 节点
            NodeList urlSetNodes = document.getElementsByTagName("urlset");
            // 遍历所有 urlset 节点
            for (int i = 0; i < urlSetNodes.getLength(); i++) {
                Node urlsetNode = urlSetNodes.item(i);
                if (!(urlsetNode.getNodeType() == Node.ELEMENT_NODE)) {
                    continue;
                }
                // 获取 url 节点
                NodeList urlNodes = ((Element) urlsetNode).getElementsByTagName("url");
                // 遍历所有 url 节点
                for (int j = 0; j < urlNodes.getLength(); j++) {
                    Node urlNode = urlNodes.item(j);
                    if (!(urlNode.getNodeType() == Node.ELEMENT_NODE)) {
                        continue;
                    }
                    // 获取 loc 子节点
                    NodeList locNodes = ((Element) urlNode).getElementsByTagName("loc");
                    if (locNodes.getLength() <= 0) {
                        continue;
                    }
                    // 获取 loc 节点的文本内容
                    String locValue = locNodes.item(0).getTextContent();
                    urlList.add(locValue);
                }
            }
            log.warn("获取到的站点地址列表为: {}", JsonUtils.toString(urlList));
        } catch (Exception exception) {
            log.error("获取站点地图的 url 失败！", exception);
        }
        return urlList;
    }

    private BaiDuPushConfigDto getBaiDuPushConfig() {
        final String baiDuPushConfigString = dictionaryManager.getByKey(GlobalConstants.BAI_DU_PUSH_CONFIG_DICT_KEY);
        BaiDuPushConfigDto baiDuPushConfigDto = JsonUtils.parseObject(baiDuPushConfigString, BaiDuPushConfigDto.class);
        if (Objects.isNull(baiDuPushConfigDto)) {
            baiDuPushConfigDto = new BaiDuPushConfigDto();
        }
        return baiDuPushConfigDto;
    }

    /**
     * 每天1点执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void pushToBaidu() {
        final BaiDuPushConfigDto baiDuPushConfig = getBaiDuPushConfig();
        final List<String> siteUrlList = getSiteUrlList(baiDuPushConfig.getSiteMapUrl());
        Safes.of(baiDuPushConfig.getPushUrlMap())
                .forEach((domain, pushUrl) -> pushOne(domain, pushUrl, baiDuPushConfig, siteUrlList));
    }

    private static String replaceDomain(String originalURL, String domain, String oldDomain) {
        try {
            // 解析URL
            final URL url = new URL(originalURL);
            // 构建新的URL，使用新的域名
            final URL newURL = new URL(url.getProtocol(), domain, url.getPort(), url.getFile());
            // 返回新的URL
            return newURL.toString();
        } catch (MalformedURLException e) {
            log.error("##替换域名失败！url={}, domain={}, oldDomain={}", originalURL, domain, oldDomain, e);
            throw new RuntimeException(e);
        }
    }

    private void pushOne(String domain, String pushUrl, BaiDuPushConfigDto baiDuPushConfig, List<String> siteUrlList) {
        final String urlMapString = dictionaryManager.getByKey(GlobalConstants.BAI_DU_HAS_PUSHED_URL_MAP_DICT_KEY);
        final Map<String, List<String>> hasPushedMap = JsonUtils.parseMap(urlMapString);
        final List<String> hasPushedList = CollectionUtils.isEmpty(hasPushedMap.get(domain)) ? Lists.newArrayList() : hasPushedMap.get(domain);
        final List<String> curNeedPushedUrls = Lists.newArrayList();
        Safes.of(siteUrlList).stream()
                .filter(url -> !hasPushedList.contains(url))
                .forEach(url -> {
                    if (curNeedPushedUrls.size() >= baiDuPushConfig.getOnePushCount()) {
                        return;
                    }
                    url = replaceDomain(url, domain, baiDuPushConfig.getSiteMapDomain());
                    curNeedPushedUrls.add(url);
                });
        log.warn("当前需要推送的 url 列表为: {}", JsonUtils.toString(curNeedPushedUrls));
        String fileContent = String.join("\n", curNeedPushedUrls);
        log.warn("当前需要推送的 url 列表为: {}", fileContent);
        // 创建HttpRequest对象
        HttpRequest request = HttpRequest.post(pushUrl)
                // 设置请求头
                .header("Content-Type", "text/plain")
                // 设置请求体，使用fileContent作为请求体内容
                .body(fileContent);
        // 发送请求并获取响应
        try {
            HttpResponse response = request.execute();
            // 获取响应状态码
            int statusCode = response.getStatus();
            BaiDuPushResDto baiDuPushResult = JsonUtils.parseObject(response.body(), BaiDuPushResDto.class);
            log.info("向百度推送相应的结果，推送的结果为: statusCode={}, result={}", statusCode, response.body());
            if (statusCode == 200 && !CollectionUtils.isEmpty(curNeedPushedUrls)) {
                hasPushedList.addAll(curNeedPushedUrls);
                hasPushedMap.put(domain, hasPushedList);
                emailNotifySend(fileContent, baiDuPushResult, curNeedPushedUrls.size(), hasPushedList.size(), domain);
                dictionaryManager.saveOrUpdateByKey(GlobalConstants.BAI_DU_HAS_PUSHED_URL_MAP_DICT_KEY,
                        JsonUtils.toString(hasPushedMap));
            }
        } catch (Exception exception) {
            log.error("推送出现异常！", exception);
        }

    }

    private void emailNotifySend(String fileContent, BaiDuPushResDto result, Integer curPushSize, Integer totalSize, String domain) {
        if (Objects.isNull(result)) {
            return;
        }
        final String subject = String.format("Z 不殊站点的【 %s 站点推送百度报告】- %s ", domain, DateUtils.getNowTimeByTransverse());
        String text = """
                 推送的文章列表为:
                   %s
                 成功推送数 %s
                 失败推送数 %s
                 当日剩余推送数 %s
                 总计已经推送连接数 %s
                """;
        text = String.format(text, fileContent, result.getSuccess(), curPushSize - result.getSuccess(),
                result.getRemain(), totalSize);
        qqSendEmailService.sendEmail(subject, text, GlobalConstants.emailConfigDto.getUserSenderEmail());
    }
}
