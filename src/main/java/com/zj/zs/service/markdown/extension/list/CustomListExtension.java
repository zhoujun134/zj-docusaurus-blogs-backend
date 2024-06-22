package com.zj.zs.service.markdown.extension.list;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * @ClassName CustomListExtension
 * @Author zj
 * @Description
 * @Date 2024/6/1 12:03
 * @Version v1.0
 **/
public class CustomListExtension implements HtmlRenderer.HtmlRendererExtension {
    @Override
    public void rendererOptions(@NotNull MutableDataHolder mutableDataHolder) {

    }


    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        htmlRendererBuilder.nodeRendererFactory(new CustomListNodeRenderer.Factory());
    }

    public static CustomListExtension create() {
        return new CustomListExtension();
    }
}
