package com.sunsea.parkinghere.framework.email.impl;

import java.util.Date;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.framework.edm.SecurityMessage;
import com.sunsea.parkinghere.framework.edm.SecurityMessageHandler;
import com.sunsea.parkinghere.framework.edm.akka.SecurityEdmCenterLifecycle;
import com.sunsea.parkinghere.framework.email.EmailMessage;
import com.sunsea.parkinghere.framework.email.EmailMessageService;

@Service
public class EmailMessageServiceImpl implements
                                    EmailMessageService,
                                    InitializingBean,
                                    SecurityMessageHandler {
    
    private static Log logger = LogFactory.getLog(EmailMessageServiceImpl.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    public void sendEmail(final EmailMessage emailMessage) {
        if (emailMessage == null) {
            return;
        }
        
        try {
            mailSender.send(new MimeMessagePreparator() {
                
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    InternetAddress fromAddress = new InternetAddress(emailMessage.getFrom(),
                                                                      emailMessage.getSender());
                    InternetAddress[] replyToAddresses = new InternetAddress[] { fromAddress };
                    
                    mimeMessage.setRecipient(Message.RecipientType.TO,
                                             new InternetAddress(emailMessage.getTo()));
                    mimeMessage.setFrom(fromAddress);
                    mimeMessage.setReplyTo(replyToAddresses);
                    mimeMessage.setSubject(emailMessage.getSubject());
                    mimeMessage.setSentDate(new Date());
                    mimeMessage.setDataHandler(new DataHandler(emailMessage.getMessage(),
                                                               "text/html; charset=UTF-8"));
                }
                
            });
            
            logger.info(String.format("Sent email notification to %s; Email subject: %s",
                                      emailMessage.getTo(),
                                      emailMessage.getSubject()));
        }
        catch (Exception ex) {
            logger.error("Exception occurred while trying to send email", ex);
        }
    }
    
    public void handle(SecurityMessage message) {
        if (message.getMessage() instanceof EmailMessage) {
            EmailMessage emailMessage = (EmailMessage) message.getMessage();
            sendEmail(emailMessage);
        }
    }
    
    public void afterPropertiesSet() throws Exception {
        SecurityEdmCenterLifecycle.registerMessageHandler(EmailMessage.EMAIL,
                                                          this);
    }
}
