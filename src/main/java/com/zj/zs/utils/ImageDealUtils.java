package com.zj.zs.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

/**
 * @ClassName ImageDealUtils
 * @Author zj
 * @Description
 * @Date 2024/7/12 00:08
 * @Version v1.0
 **/
public class ImageDealUtils {
    public static void suoFang() throws IOException {
        Thumbnails.of("/Users/zj/Downloads/test3.png")
                .scale(0.5)
                .toFile("/Users/zj/Downloads/test4.png");
    }

    public static void main(String[] args) throws IOException {
        suoFang();
    }
}
