package com.zj.zs.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @ClassName DateUtils
 * @Author zj
 * @Description
 * @Date 2024/6/15 16:58
 * @Version v1.0
 **/
@Slf4j
public class DateUtils {

    // 创建一个格式化器，定义自定义的日期时间格式
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    private static final DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static String format(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return "";
        }
        try {
            // 使用自定义的格式化器解析日期时间字符串
            return formatter.format(dateTime);
        } catch (Exception e) {
            log.error("日期格式化异常", e);
            return "";
        }
    }

    public static String formatByColon(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return "";
        }
        try {
            // 使用自定义的格式化器解析日期时间字符串
            return formatter3.format(dateTime);
        } catch (Exception e) {
            log.error("日期格式化异常", e);
            return "";
        }
    }

    public static String formatByTransverse(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return "";
        }
        try {
            // 使用自定义的格式化器解析日期时间字符串
            return formatter2.format(dateTime);
        } catch (Exception e) {
            log.error("日期格式化异常", e);
            return "";
        }
    }

    /**
     * 返回当前时间 yyyy-MM-dd-HH-mm-ss 格式
     * @return yyyy-MM-dd-HH-mm-ss 格式 时间值
     */
    public static String getNowTimeByTransverse() {
        try {
            // 使用自定义的格式化器解析日期时间字符串
            return formatter2.format(LocalDateTime.now());
        } catch (Exception e) {
            log.error("日期格式化异常", e);
            return "";
        }
    }

    /**
     * 返回当前时间 yyyy-MM-dd HH:mm:ss 格式
     * @return yyyy-MM-dd HH:mm:ss 格式 时间值
     */
    public static String getNowTimeForColon() {
        try {
            // 使用自定义的格式化器解析日期时间字符串
            return formatter3.format(LocalDateTime.now());
        } catch (Exception e) {
            log.error("日期格式化异常", e);
            return "";
        }
    }
}
