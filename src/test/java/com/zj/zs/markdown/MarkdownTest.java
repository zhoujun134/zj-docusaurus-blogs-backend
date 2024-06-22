package com.zj.zs.markdown;

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
import com.zj.zs.service.markdown.extension.img.CustomImageExtension;
import com.zj.zs.service.markdown.extension.list.CustomListExtension;
import com.zj.zs.service.markdown.extension.preCode.CustomPreCodeExtension;
import com.zj.zs.service.markdown.extension.toc.CustomTocExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static com.zj.zs.service.markdown.extension.toc.CustomTocNodeRenderer.TOC_HTML;

/**
 * @ClassName MarkdownTest
 * @Author zj
 * @Description markdown 文件测试
 * @Date 2024/3/17 16:00
 * @Version v1.0
 **/
public class MarkdownTest {

    final private static DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
                    TocExtension.create(),
                    CustomTocExtension.create(),
                    CustomImageExtension.create(),
                    CustomListExtension.create(),
                    CustomPreCodeExtension.create(),
                    // 自定义扩展，为<pre>标签添加line-numbers的class，用于prism库代码左侧行号展示
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
    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS)
            // 缩进 2 字符
            .indentSize(2)
            .build();

    public static void main(String[] args) throws IOException {

//        List<String> lines = Files.readAllLines(Path.of("/Users/zj/IdeaProjects/zbusSite/docs/notes/redis 相关.md"));
        List<String> lines = Files.readAllLines(Path.of("/Users/zj/IdeaProjects/work/zjBootBlog/README.md"));
        String content = "[TOC] \n \n " + String.join("\n", lines);
        Document document = PARSER.parse(content);
        String html = RENDERER.render(document);
        String toc = TOC_HTML.get(document);
//        System.out.println("toc-string: " + toc);
        System.out.println("test html : " + html);
    }
}
