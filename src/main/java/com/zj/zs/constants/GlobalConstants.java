package com.zj.zs.constants;

import com.zj.zs.domain.dto.article.CategoryDto;
import com.zj.zs.domain.dto.config.QQEmailConfigDto;
import com.zj.zs.domain.dto.config.TencentConfigDto;
import com.zj.zs.domain.dto.docusaurus.DocusaurusPublishShellConfigDto;

/**
 * @ClassName GlobalConstants
 * @Author zj
 * @Description
 * @Date 2024/3/10 15:23
 * @Version v1.0
 **/
public class GlobalConstants {
    public final static String DEFAULT_USER_NAME = "zs-boot-default-user";
    public final static String TENCENT_CONFIG_KEY = "zs-boot-tencent-config";
    public final static String QQ_EMAIL_CONFIG_KEY = "zs-boot-qq-email-config";
    public final static CategoryDto DEFAULT_CATEGORY_ID = new CategoryDto("zs-suibi", "随笔");
    public final static String DEFAULT_IMAGE_CATEGORY = "blog-content";
    /**
     * redis 缓存 key
     */
    public final static String ACCESS_LOG_REDIS_KEY_PREFIX = "zs.access.log.ip-%s";
    public final static String ACCESS_LOG_REDIS_EMAIL_KEY_PREFIX = "zs.access.log.email.ip-%s";
    /**
     * redis 缓存 key 过期时间
     */
    public final static int ACCESS_LOG_REDIS_KEY_EXPIRE_MINUTE = 1;
    /**
     * 一个ip 一分钟内访问的最大次数
     */
    public final static int ACCESS_LOG_ONE_IP_VISITED_MAX_ONE_MINUTE = 20;
    public final static String ACCESS_BLACK_IP_KEY = "zs.access.ip.black.list.key";
    public final static String ACCESS_IP_KEY = "x-real-ip";
    public final static String ZS_BLOG_SESSION_ID = "zs-blog-session-id";
    public static TencentConfigDto tencentConfigDto = null;
    public static QQEmailConfigDto emailConfigDto = null;

    public static final String ARTICLE_ABSTRACT_PROMOTE = "根据文章内容，请使用中文生成文章摘要，返回的结果只需要包含实际的摘要内容即可，如果文章内容较少，可以直接返回空, 文章内容如下: ";

    public static final String ARTICLE_MD_CREATE_TAG_TITLE_TEMPLATE = """
            ---
            slug: ${tilePath}
            title: ${tile}
            authors:
              name: ${authorName}
              title: 不要等! 不管想做什么, 都要立刻动起来。
              url: https://github.com/zhoujun134
              image_url: https://img.zbus.top/zbus/logo.jpg
            tags: [${tagListString}]
            image: ${blogImage}
            ---
            """;

    public static DocusaurusPublishShellConfigDto docusaurusPublishConfig = null;

    public static String DOCUSAURUS_CONFIG_DICT_KEY = "zs.docusaurus.config.dict.key";

}
