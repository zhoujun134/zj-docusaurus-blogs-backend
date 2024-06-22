package com.zj.zs.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @ClassName FileUtils
 * @Author zj
 * @Description
 * @Date 2024/6/15 18:38
 * @Version v1.0
 **/
@Slf4j
public class FileUtils {

    /**
     * 向 filePath 写入一个内容
     * @param content 待写入的内容
     * @param filePath 文件路径
     */
    public static void writeToNewFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 写入内容到文件
            writer.write(content);
        } catch (IOException e) {
            // 处理可能发生的异常
            log.error("writeToNewFile: 失败！e: ", e);
        }
    }

    /**
     * 文件目录生成
     * @param parentPath 父目录
     * @param year 年份
     * @param month 月份
     */
    public static String fileDirGenerate(String parentPath, int year, int month) {
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + monthStr;
        }
        final String path = String.format("%s/%s/%s", parentPath, year, monthStr);
        return fileDirGenerate(path);
    }

    /**
     * 文件目录生成
     * @param parentPath 目录
     */
    public static String fileDirGenerate(String parentPath) {
        if (StringUtils.isBlank(parentPath)) {
            return parentPath;
        }
        final Path dirPath = Path.of(parentPath);
        final boolean exists = Files.exists(dirPath);
        if (!exists) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                log.error("fileDirGenerate: 创建目录失败！", e);
            }
        }
        log.info("fileDirGenerate:生成结果 {}", Files.exists(dirPath));
        return parentPath;
    }
}
