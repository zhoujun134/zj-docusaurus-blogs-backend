package com.zj.zs.service.markdown.extension.preCode;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * @ClassName CustomPreCodeExtension
 * @Author zj
 * @Description
 * @Date 2024/6/1 12:29
 * @Version v1.0
 **/
public class CustomPreCodeExtension implements HtmlRenderer.HtmlRendererExtension {

    @Override
    public void rendererOptions(@NotNull MutableDataHolder mutableDataHolder) {

    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        htmlRendererBuilder.nodeRendererFactory(new CustomPreCodeNodeRenderer.Factory());
    }

    public static CustomPreCodeExtension create() {
        return new CustomPreCodeExtension();
    }
}
