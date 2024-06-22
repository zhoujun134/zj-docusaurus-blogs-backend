package com.zj.zs.service.markdown.extension.img;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * @ClassName CustomExtension
 * @Author zj
 * @Description
 * @Date 2024/5/31 08:28
 * @Version v1.0
 **/
public class CustomImageExtension implements HtmlRenderer.HtmlRendererExtension {
    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        htmlRendererBuilder.nodeRendererFactory(new CustomImageNodeRenderer.Factory());
    }

    public static CustomImageExtension create() {
        return new CustomImageExtension();
    }
}
