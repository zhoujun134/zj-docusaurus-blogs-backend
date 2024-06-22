package com.zj.zs.domain.dto.markdown;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName MarkdownContentDto
 * @Author zj
 * @Description
 * @Date 2024/6/1 14:22
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkdownContentDto {

    private String tocListString;

    private String content;
}
