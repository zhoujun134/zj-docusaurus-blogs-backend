package com.zj.zs.service.markdown.extension.toc;

import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName CustomNodeRenderer
 * @Author zj
 * @Description
 * @Date 2024/5/31 08:27
 * @Version v1.0
 **/
@Slf4j
public class CustomTocNodeRenderer implements NodeRenderer {
    final public static DataKey<String> TOC_HTML = new DataKey<>("TOC_HTML", "");

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new CustomTocNodeRenderer();
        }
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(tocBlockNodeRenderingHandler());
        return set;
    }

    private NodeRenderingHandler<TocBlock> tocBlockNodeRenderingHandler() {
        final TocBlockCustomNodeRenderer tocBlockCustomNodeRenderer = new TocBlockCustomNodeRenderer();
        return new NodeRenderingHandler<>(TocBlock.class, tocBlockCustomNodeRenderer);
    }
    public static class TocBlockCustomNodeRenderer  implements NodeRenderingHandler.CustomNodeRenderer<TocBlock> {
        @Override
        public void render(@NotNull TocBlock fencedCodeBlock, @NotNull NodeRendererContext context, @NotNull HtmlWriter htmlWriter) {
            NodeRendererContext subContext = context.getDelegatedSubContext(true);
            subContext.delegateRender();
            String tocText = ((LineAppendable) subContext.getHtmlWriter()).toString(0, 0);
            context.getDocument().set(TOC_HTML, tocText);
        }
    }
}
