package com.zj.zs.domain.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @ClassName CategoryDto
 * @Author zj
 * @Description
 * @Date 2024/4/14 21:58
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class CategoryDto {

    /**
     * 分类 id
     */
    private String categoryId;
    /**
     * 分类名称
     */
    @NotBlank(message = "添加列表时，类别名称不能为空！")
    private String name;
}
