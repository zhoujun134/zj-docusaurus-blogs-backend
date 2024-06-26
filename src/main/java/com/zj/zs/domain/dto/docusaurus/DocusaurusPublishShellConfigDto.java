package com.zj.zs.domain.dto.docusaurus;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class DocusaurusPublishShellConfigDto {

    /**
     * docusaurus 项目路径
     */
    @NotBlank(message = "docusaurus 项目路径 不能为空")
    private String docusaurusProjectPath;

    /**
     * docusaurus build 历史版本文件路径
     */
    @NotBlank(message = "docusaurus build 历史版本文件路径 不能为空")
    private String buildHistoryPath;

    /**
     * 站点发布的目录位置
     */
    @NotBlank(message = "站点发布的目录位置 不能为空")
    private String nginxSitePath;

}
