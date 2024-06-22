package com.zj.zs.config;

import com.sun.mail.util.MailSSLSocketFactory;
import com.zj.zs.constants.GlobalConstants;
import com.zj.zs.dao.DictionaryManager;
import com.zj.zs.domain.dto.config.QQEmailConfigDto;
import com.zj.zs.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.Properties;

import static com.zj.zs.constants.GlobalConstants.QQ_EMAIL_CONFIG_KEY;

/**
 * @ClassName QQEmailConfiguration
 * @Author zj
 * @Description
 * @Date 2024/4/11 22:18
 * @Version v1.0
 **/
@Slf4j
@Configuration
public class QQEmailConfiguration {

    @Resource
    private DictionaryManager dictionaryManager;
    private static QQEmailConfigDto emailConfigDto = new QQEmailConfigDto();

    @PostConstruct
    public void initQQEmailConfig() {
        String jsonConfig = dictionaryManager.getByKey(QQ_EMAIL_CONFIG_KEY);
        if (StringUtils.isBlank(jsonConfig)) {
            return;
        }
        QQEmailConfigDto qqEmailConfigDB = JsonUtils.parseObject(jsonConfig, QQEmailConfigDto.class);
        if (Objects.nonNull(qqEmailConfigDB)) {
            emailConfigDto = qqEmailConfigDB;
            GlobalConstants.emailConfigDto = qqEmailConfigDB;
            log.info("##initQQEmailConfig 读取到 qq 邮箱的存储配置!");
        }
    }

    @Bean(name = "emailSession")
    public Session getMailSession() {
        if (emailConfigDto == null || emailConfigDto.isBlankUserNameAndPassword()) {
            log.warn("##getMailSession: 获取 email 配置信息失败");
            return Session.getInstance(getDefaultProps());
        }
        Properties props = getDefaultProps();
        MailSSLSocketFactory msf = null;
        try {
            // 开启ssl加密（并不是所有的邮箱服务器都需要，但是qq邮箱服务器是必须的）
            msf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException exception) {
            log.error("##getMailSession:开启 ssl 失败！", exception);
            return null;
        }
        msf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", msf);
        //获取Session会话实例（javamail Session与HttpSession的区别是Javamail的Session只是配置信息的集合）
        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                //用户名密码验证（取得的授权吗）
                return new PasswordAuthentication(emailConfigDto.getUserName(),
                        emailConfigDto.getUserPassword());
            }
        });
    }

    @NotNull
    private static Properties getDefaultProps() {
        // 用于读取配置文件
        Properties props = new Properties();
        // 开启Debug调试
        props.setProperty("mail.debug", "false");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 发送邮件服务器的主机名
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        // 端口号
        props.setProperty("mail.smtp.port", "465");
        // 发送邮件协议
        props.setProperty("mail.transport.protocol", "smtp");
        return props;
    }
}
