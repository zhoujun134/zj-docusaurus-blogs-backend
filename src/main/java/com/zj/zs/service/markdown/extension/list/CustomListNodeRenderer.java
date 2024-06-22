package com.zj.zs.service.markdown.extension.list;

import com.vladsch.flexmark.ast.BulletList;
import com.vladsch.flexmark.ast.OrderedList;
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
 * @ClassName CustomListNodeRenderer
 * @Author zj
 * @Description
 * @Date 2024/6/1 12:04
 * @Version v1.0
 **/
public class CustomListNodeRenderer implements NodeRenderer {
    @Override
    public @Nullable Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(bulletListNodeRenderingHandler());
        set.add(orderedListNodeRenderingHandler());
        return set;
    }

    private NodeRenderingHandler<BulletList> bulletListNodeRenderingHandler() {
        return new NodeRenderingHandler<>(BulletList.class, new BulletListCustomNodeRenderer());
    }

    private NodeRenderingHandler<OrderedList> orderedListNodeRenderingHandler() {
        return new NodeRenderingHandler<>(OrderedList.class, new OrderedListCustomNodeRenderer());
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new CustomListNodeRenderer();
        }
    }

    /**
     * 有序列表处理
     */
    public static class OrderedListCustomNodeRenderer implements NodeRenderingHandler.CustomNodeRenderer<OrderedList> {
        @Override
        public void render(@NotNull OrderedList node, @NotNull NodeRendererContext context, @NotNull HtmlWriter html) {
            html.line().indent();
            html.withAttr()
                    .attr("class", "zj-custom-ul")
                    .tag("ol");
            // 渲染列表项
            context.renderChildren(node);
            html.tag("/ol");
        }
    }
    /**
     * 无序列表处理
     */

    public static class BulletListCustomNodeRenderer implements NodeRenderingHandler.CustomNodeRenderer<BulletList> {
        @Override
        public void render(@NotNull BulletList node, @NotNull NodeRendererContext context, @NotNull HtmlWriter html) {

            html.line().indent();
            html.withAttr()
                    .attr("class", "zj-custom-ul")
                    .tag("ul");
            // 渲染列表项
            context.renderChildren(node);
            html.tag("/ul");
        }
    }
}
