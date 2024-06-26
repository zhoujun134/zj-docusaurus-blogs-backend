package com.zj.zs.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
public class JsonUtils {

    private final static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    public static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String toString(Object data) {
        if (Objects.isNull(data)) {
            return "";
        }
        try {
            return gson.toJson(data);
        } catch (Exception e) {
            log.error("JsonUtils######toString： error data={}", data, e);
            return "";
        }
    }

    public static <T> T parseObject(String text, Class<T> tClass) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        try {
            return gson.fromJson(text, tClass);
        } catch (Exception e) {
            log.error("JsonUtils######parseObject： error data={}", text, e);
            return null;
        }
    }
}
