package com.zj.zs.service.markdown.extension.preCode;

import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName CustomPreCodeNodeRenderer
 * @Author zj
 * @Description
 * @Date 2024/6/1 12:35
 * @Version v1.0
 **/
public class CustomPreCodeNodeRenderer implements NodeRenderer {
    final private boolean codeContentBlock;

    public CustomPreCodeNodeRenderer(DataHolder options) {
        codeContentBlock = Parser.FENCED_CODE_CONTENT_BLOCK.get(options);
    }
    @Override
    public @Nullable Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(preCodeNodeRenderingHandler());
        set.add(codeNodeRenderingHandler());
        return set;
    }

    private NodeRenderingHandler<FencedCodeBlock> preCodeNodeRenderingHandler() {
        return new NodeRenderingHandler<>(FencedCodeBlock.class, new PreCodeCustomNodeRenderer(codeContentBlock));
    }
    private NodeRenderingHandler<Code> codeNodeRenderingHandler() {
        return new NodeRenderingHandler<>(Code.class, new CodeCustomNodeRenderer());
    }


    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new CustomPreCodeNodeRenderer(options);
        }
    }

    public static class CodeCustomNodeRenderer implements NodeRenderingHandler.CustomNodeRenderer<Code> {
        @Override
        public void render(@NotNull Code node, @NotNull NodeRendererContext context, @NotNull HtmlWriter html) {
            html.line();
            html.srcPosWithTrailingEOL(node.getChars())
                    .withAttr()
                    .attr("class", "language-shell")
                    .tag("code")
                    .openPre();
            html.text(node.getText());
            html.tag("/code");
        }
    }

    public static class PreCodeCustomNodeRenderer implements NodeRenderingHandler.CustomNodeRenderer<FencedCodeBlock> {
        private final boolean codeContentBlock;

        public PreCodeCustomNodeRenderer(boolean codeContentBlock) {
            this.codeContentBlock = codeContentBlock;
        }

        @Override
        public void render(@NotNull FencedCodeBlock node, @NotNull NodeRendererContext context, @NotNull HtmlWriter html) {

            BasedSequence info = node.getInfo();
            html.line();
            html.srcPosWithTrailingEOL(node.getChars())
                    .withAttr()
                    .tag("pre")
                    .openPre();
            if (info.isNotNull() && !info.isBlank()) {
                BasedSequence language = node.getInfoDelimitedByAny(CharPredicate.SPACE_TAB);
                // 增加行号控制
                String className = context.getHtmlOptions().languageClassPrefix + language.unescape() + " line-numbers";
                html.attr("class", className);
            } else {
                String noLanguageClass = context.getHtmlOptions().noLanguageClass.trim();
                if (StringUtils.isNotBlank(noLanguageClass)) {
                    html.attr("class", noLanguageClass);
                }
            }
            html.srcPosWithTrailingEOL(node.getContentChars()).withAttr(CoreNodeRenderer.CODE_CONTENT).tag("code");
            if (codeContentBlock) {
                context.renderChildren(node);
            } else {
                html.text(node.getContentChars().normalizeEndWithEOL());
            }
            html.tag("/code");
            html.tag("/pre").closePre();
            html.lineIf(context.getHtmlOptions().htmlBlockCloseTagEol);
        }
    }
}
