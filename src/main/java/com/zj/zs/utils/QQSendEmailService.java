package com.zj.zs.utils;

import com.zj.zs.constants.GlobalConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

/**
 * 发送邮件的工具类QQ
 * <p>maven 依赖 java mail</p>
 * <pre>
 *         <dependency>
 *             <groupId>com.sun.mail</groupId>
 *             <artifactId>javax.mail</artifactId>
 *             <version>1.6.2</version>
 *         </dependency>
 * </pre>
 */
@Slf4j
@Component
public class QQSendEmailService {

    @Resource(name = "emailSession")
    private Session emailSession;

    /**
     * 目前发送到163 qq个人邮箱可以，企业邮箱有问题
     *
     * @param subject     邮件主题
     * @param text        邮件内容纯文本
     * @param mailAddress 邮件地址
     */
    public void sendEmail(String subject, String text, String... mailAddress) {
        try {
            if (Objects.isNull(GlobalConstants.emailConfigDto)) {
                log.warn("QQSendEmailService#sendEmail 没有配置邮件信息，不进行邮件发送: subject={}, text={}, mailAddress={}",
                        subject, text, mailAddress);
                return;
            }
            if (Objects.isNull(mailAddress) || mailAddress.length == 0
                    || StringUtils.isAnyBlank(subject, text)) {
                log.warn("QQSendEmailService#sendEmail 传递的参数存在空值，请检查: subject={}, text={}, mailAddress={}",
                        subject, text, mailAddress);
                return;
            }
            Session session = emailSession;
            // 抽象类MimeMessage为实现类 消息载体封装了邮件的所有消息
            Message message = new MimeMessage(session);
            // 设置邮件主题
            message.setSubject(subject);
            // 封装需要发送电子邮件的信息
            message.setText(text);
            // 设置发件人地址
            message.setFrom(new InternetAddress(GlobalConstants.emailConfigDto.getUserSenderEmail()));
            // 此类的功能是发送邮件 又会话获得实例
            Transport transport = session.getTransport();
            // 开启连接
            transport.connect();
            // 设置收件人地址邮件信息
            for (String address : mailAddress) {
                transport.sendMessage(message, new Address[]{new InternetAddress(address)});
                //邮件发送后关闭信息
                transport.close();
            }
        } catch (Exception exception) {
            log.warn("QQSendEmailService##sendEmail: 发送邮件服务出现了异常！", exception);
        }
    }

}
