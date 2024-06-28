package com.zj.zs.utils;

import com.google.common.collect.Lists;
import com.zj.zs.domain.dto.docusaurus.DocusaurusPublishShellConfigDto;
import com.zj.zs.domain.dto.docusaurus.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class SystemCommandExecutor {

    // 创建一个格式化器，定义自定义的日期时间格式
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    static final List<String> commands = Lists.newArrayList(
            // 添加这行来确保命令失败时停止执行
            "set -e;",
            // 进入到指定的文件目录
            "cd /Users/zhoujun09/IdeaProjects/study/my-docus;",
            "pwd;",
            // 使用 docusaurus 命令 build 项目
            "npm run build;",
            // 将 build 的文件信息移动到 nginx 项目下，这里 /opt/soft/myDocsSite 为我们网页部署的地址
            "cp -r build/* /opt/soft/myDocsSite;",
            // 重启 nginx
            "nginx -s reload;",
            "echo '===================启动成功===============';");

    public static void removeBuildHistoryVersion(String buildHistoryPath) {
        if (StringUtils.isBlank(buildHistoryPath)) {
            return;
        }
        File directory = new File(buildHistoryPath);
        // 检查目录是否存在
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The specified path is not a valid directory.");
            return;
        }
        // 获取所有文件夹并按名称升序排列
        File[] directories = directory.listFiles((dir, name) -> new File(dir, name).isDirectory());
        if (directories == null) {
            return;
        }
        // 对目录数组进行排序
        Arrays.sort(directories, Comparator.comparing(File::getName));
        // 保留最新的10个目录
        for (int i = 10; i < directories.length; i++) {
            File oldDirectory = directories[i];
            if (!oldDirectory.exists()) {
                continue;
            }
            try {
                Files.deleteIfExists(oldDirectory.toPath());
            } catch (IOException e) {
                log.warn("##removeBuildHistoryVersion: error: ", e);
            }
        }
    }


    public static List<String> initCommandsByConfig(DocusaurusPublishShellConfigDto config) {
        String buildHistoryPath = config.getBuildHistoryPath();
        if (StringUtils.isBlank(config.getBuildHistoryPath())) {
            removeBuildHistoryVersion(buildHistoryPath);
            String nowTimeByTransverse = DateUtils.getNowTimeByTransverse();
            buildHistoryPath = String.format("%s/%s", buildHistoryPath, nowTimeByTransverse);
        }
        return Lists.newArrayList(
                // 添加这行来确保命令失败时停止执行
                "set -e;",
                // 进入到指定的文件目录
                String.format("cd %s;", config.getDocusaurusProjectPath()),
                "pwd;",
                "npm install;",
                // 使用 docusaurus 命令 build 项目
                "npm run build;",
                // 上一个版本进行备份
                StringUtils.isBlank(buildHistoryPath) ? "" : String.format("rm -rf %s;", buildHistoryPath),
                StringUtils.isBlank(buildHistoryPath) ? "" : String.format("mkdir %s;", buildHistoryPath),
                StringUtils.isBlank(buildHistoryPath) ? "" : String.format("cp -r %s %s;", config.getNginxSitePath(), buildHistoryPath),
                // 将 build 的文件信息移动到 nginx 项目下，这里 /opt/soft/myDocsSite 为我们网页部署的地址
                String.format("cp -r build/* %s;", config.getNginxSitePath()),
                // 重启 nginx
                "nginx -s reload;",
                "echo '===================启动成功===============';");

    }
    public static void main(String[] args) {
        ExecuteResult executeResult = executeCommands(commands);
        System.out.println(JsonUtils.toString(executeResult));
    }

    public static ExecuteResult executeCommands(List<String> commands) {
        // 定义要执行的命令
        String commandStr = String.join(" ", commands);
        // 创建ProcessBuilder对象
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/zsh", "-c", commandStr);

        try {
            // 启动进程
            Process process = processBuilder.start();
            List<String> infoMessages = Lists.newArrayList();
            List<String> errorMessages = Lists.newArrayList();
            // 读取命令执行的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                LocalDateTime nowTime = LocalDateTime.now();
                String message = String.format("%s Shell command execute INFO: %s", formatter.format(nowTime), line);
                infoMessages.add(message);
            }

            // 等待进程结束并获取退出状态
            int exitCode = process.waitFor();
            log.info("命令执行完毕，退出状态码: " + exitCode);

            // 读取错误输出
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                LocalDateTime nowTime = LocalDateTime.now();
                String message = String.format("%s Shell command execute ERROR: %s", formatter.format(nowTime), line);
                errorMessages.add(message);
            }
            return new ExecuteResult()
                    .setInfoMessage(infoMessages)
                    .setErrorMessage(errorMessages)
                    .setExitCode(exitCode);
        } catch (IOException | InterruptedException e) {
           log.error("代码执行器执行异常！e: {}", e.getMessage(), e);
        }
        return null;
    }
}
