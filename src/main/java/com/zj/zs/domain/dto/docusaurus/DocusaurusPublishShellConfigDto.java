package com.zj.zs.domain.dto.docusaurus;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DocusaurusPublishShellConfigDto {

    /**
     * docusaurus 项目路径
     */
    private String docusaurusProjectPath;

    /**
     * docusaurus build 历史版本文件路径
     */
    private String buildHistoryPath;

    /**
     * 站点发布的目录位置
     */
    private String nginxSitePath;

}
