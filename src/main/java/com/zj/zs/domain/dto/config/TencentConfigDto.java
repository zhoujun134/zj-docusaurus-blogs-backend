package com.zj.zs.domain.dto.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TencentConfigDto
 * @Author zj
 * @Description
 * @Date 2024/4/11 22:06
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TencentConfigDto {
    /**
     * cos 存储桶名称
     */
    private String bucketName;

    /**
     * 密钥 id
     */
    private String secretId;

    /**
     * 密钥 key
     */
    private String secretKey;

    /**
     * 会话 token
     */
    private String sessionToken;

    /**
     * 存储桶区域
     */
    private String region;
    /**
     * 图片的域名
     */
    private String imagePrefix;

    /**
     * 图片的域名
     */
    private String imageHostName;
}
