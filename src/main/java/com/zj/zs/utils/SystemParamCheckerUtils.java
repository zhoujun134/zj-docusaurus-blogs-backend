package com.zj.zs.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName SystemParamCheckerUtils
 * @Author zj
 * @Description
 * @Date 2024/6/18 00:48
 * @Version v1.0
 **/
@Slf4j
public class SystemParamCheckerUtils {
    // 检查npm版本
    private static String checkNpmVersion() {
        return checkVersion("npm");
    }

    // 检查Node.js版本
    private static String checkNodeVersion() {
        return checkVersion("node");
    }

    // 检查nginx是否安装
    private static String checkNginxInstallation() {
        ProcessBuilder processBuilder = new ProcessBuilder("nginx", "-v");
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String version = reader.readLine();
            String errOutVersion = errorReader.readLine();
            log.info("##checkNginxInstallation: version={}, errOutVersion={}", version, errOutVersion);
            reader.close();
            errorReader.close();
            if (StringUtils.isNotBlank(version) && version.startsWith("nginx version: nginx/")) {
                return version;
            }
            if (StringUtils.isNotBlank(errOutVersion) && errOutVersion.startsWith("nginx version: nginx/")) {
                return errOutVersion;
            }
        } catch (IOException e) {
            log.error("##checkVersion: {} is not installed: error: {}", "nginx", e.getMessage(), e);
        }
        return null;
    }

    // 通用方法检查版本
    private static String checkVersion(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command, "--version");
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String version = reader.readLine();
            if (version != null && !version.isEmpty()) {
                return version.trim();
            } else {
                return command + " is not installed";
            }
        } catch (IOException e) {
            log.error("##checkVersion: {} is not installed: error: {}", command, e.getMessage(), e);
        }
        return null;
    }
}
