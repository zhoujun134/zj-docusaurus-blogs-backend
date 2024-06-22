package com.zj.zs.service.markdown.impl;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.zj.zs.domain.dto.markdown.MarkdownContentDto;
import com.zj.zs.service.markdown.MarkdownMakerService;
import com.zj.zs.service.markdown.extension.img.CustomImageExtension;
import com.zj.zs.service.markdown.extension.list.CustomListExtension;
import com.zj.zs.service.markdown.extension.preCode.CustomPreCodeExtension;
import com.zj.zs.service.markdown.extension.toc.CustomTocExtension;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.zj.zs.service.markdown.extension.toc.CustomTocNodeRenderer.TOC_HTML;

/**
 * @ClassName MarkdownMakerServiceImpl
 * @Author zj
 * @Description
 * @Date 2024/6/1 14:09
 * @Version v1.0
 **/
@Service
@Slf4j
public class MarkdownMakerServiceImpl implements MarkdownMakerService {

    private final static DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
                    TocExtension.create(),
                    // 自定义扩展
                    CustomTocExtension.create(),
                    CustomImageExtension.create(),
                    CustomListExtension.create(),
                    CustomPreCodeExtension.create(),
                    // 系统类的扩展
                    AutolinkExtension.create(),
                    EmojiExtension.create(),
                    StrikethroughExtension.create(),
                    TaskListExtension.create(),
                    TablesExtension.create()
            ))// set GitHub table parsing options
            .set(TablesExtension.WITH_CAPTION, false)
            .set(TablesExtension.COLUMN_SPANS, false)
            .set(TablesExtension.MIN_HEADER_ROWS, 1)
            .set(TablesExtension.MAX_HEADER_ROWS, 1)
            .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
            .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
            .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
            // setup emoji shortcut options
            // uncomment and change to your image directory for emoji images if you have it setup
//                .set(EmojiExtension.ROOT_IMAGE_PATH, emojiInstallDirectory())
            .set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.GITHUB)
            .set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.IMAGE_ONLY);
    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS)
            // 缩进 2 字符
            .indentSize(2)
            .build();

    @Override
    public MarkdownContentDto generateHtmlByMarkdown(String markdown) {
        // 加上 [TOC] 生成对应的目录信息
        final String preContent = "[TOC] \n \n " + markdown;
        final Document document = PARSER.parse(preContent);
        final String content = RENDERER.render(document);
        final String tocString = TOC_HTML.get(document);
        return new MarkdownContentDto(tocString, content);
    }
}
