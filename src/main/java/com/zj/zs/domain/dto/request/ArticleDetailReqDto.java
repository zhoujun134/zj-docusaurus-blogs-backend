package com.zj.zs.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName ArticleDetailReqDTO
 * @Author zj
 * @Description
 * @Date 2024/4/6 20:05
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailReqDto {

    @NotBlank(message = "文章 ID 不能为空")
    private String articleId;
}
