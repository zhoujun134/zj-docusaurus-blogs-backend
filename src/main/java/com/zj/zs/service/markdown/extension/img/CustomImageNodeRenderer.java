package com.zj.zs.service.markdown.extension.img;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName CustomImageNodeRenderer
 * @Author zj
 * @Description
 * @Date 2024/6/1 10:35
 * @Version v1.0
 **/
public class CustomImageNodeRenderer implements NodeRenderer {
    @Override
    public @Nullable Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(imageNodeRenderingHandler());
        return set;
    }

    private NodeRenderingHandler<Image> imageNodeRenderingHandler() {
        return new NodeRenderingHandler<>(Image.class, new ImageCustomNodeRenderer());
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new CustomImageNodeRenderer();
        }
    }

    public static class ImageCustomNodeRenderer implements NodeRenderingHandler.CustomNodeRenderer<Image> {
        @Override
        public void render(@NotNull Image node, @NotNull NodeRendererContext context, @NotNull HtmlWriter html) {

            html.line();
            // 渲染包裹图片的 div 标签
            html.withAttr()
                    .attr("class", "zj-blog-content-img-container")
                    .tag("div");

            html.line();
            html.indent();
            // 开始渲染图片标签
            html.srcPosWithTrailingEOL(node.getChars()).withAttr()
                    .attr("class", "zj-blog-content-img")
                    .attr("data-zoomable", "")
                    .attr("src", node.getUrl())
                    .attr("alt", node.getText())
                    .tag("img");
            html.line();
            html.tag("/img");
            html.line();
            html.tag("/div");
            html.line();
        }
    }


}
