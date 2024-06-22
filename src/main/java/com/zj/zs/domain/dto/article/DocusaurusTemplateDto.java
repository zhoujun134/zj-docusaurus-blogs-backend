package com.zj.zs.domain.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName DocusaurusTemplateDto
 * @Author zj
 * @Description
 * @Date 2024/6/17 00:33
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocusaurusTemplateDto {

    private String templateTagString = "";

    private String fileName = "";

    private String createTime = "";

    private List<CategoryDto> categoryList;
}
