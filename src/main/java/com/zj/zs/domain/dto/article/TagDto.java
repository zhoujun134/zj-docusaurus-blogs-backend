package com.zj.zs.domain.dto.article;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @ClassName TagDto
 * @Author zj
 * @Description
 * @Date 2024/4/9 23:05
 * @Version v1.0
 **/
@Data
@Valid
public class TagDto {

    private String tagId;

    @NotBlank(message = "添加的标签名称不能为空！")
    private String tagName;
}
