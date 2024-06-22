package com.zj.zs.service.markdown;

import com.zj.zs.domain.dto.markdown.MarkdownContentDto;

/**
 * @ClassName MarkdownMakerService
 * @Author zj
 * @Description
 * @Date 2024/6/1 14:07
 * @Version v1.0
 **/
public interface MarkdownMakerService {
    MarkdownContentDto generateHtmlByMarkdown(String markdown);
}
