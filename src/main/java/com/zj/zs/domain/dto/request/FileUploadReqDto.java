package com.zj.zs.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName FileUploadDto
 * @Author zj
 * @Description
 * @Date 2024/4/11 23:00
 * @Version v1.0
 **/
@Data
public class FileUploadReqDto {
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    private String fileType;
}
