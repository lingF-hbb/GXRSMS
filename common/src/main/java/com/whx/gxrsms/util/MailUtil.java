package com.whx.gxrsms.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtil {

    @Value("${email.client.address}")
    private String senderAddress;

    @Value("${email.client.account-id}")
    private String senderAccount;

    @Value("${email.client.auth-password}")
    private String authPassword;


    public boolean SendMail(String toMail, String Msg) throws Exception {
        System.out.println(toMail);
        System.out.println(Msg);
        //发件人地址
        String senderAddress = this.senderAddress;
        //收件人地址
        String recipientAddress = toMail;
        //发件人账户名
        String senderAccount = this.senderAccount;
        //发件人账户密码(授权码非密码)
        String senderPassword = this.authPassword;
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.qq.com");

        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);

        try {
            // 创建邮件对象
            MimeMessage message = new MimeMessage(session);

            // 设置邮件发送方
            message.setFrom(new InternetAddress(senderAddress));


            // 设置邮件发送的主题<邮件标题>
            message.setSubject("邮箱验证");


            // 设置邮件发送的内容
            message.setContent(Msg, "text/html;charset=utf-8");

            Transport transport = session.getTransport();
            // 连接邮件服务器，“”中填写邮件服务器主机名
            transport.connect("smtp.qq.com", 25, senderAccount, senderPassword);
            transport.sendMessage(message, new Address[]{new InternetAddress(recipientAddress)});
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}