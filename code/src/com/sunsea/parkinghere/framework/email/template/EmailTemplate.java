package com.sunsea.parkinghere.framework.email.template;

import java.io.StringWriter;
import java.util.Map;

import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class EmailTemplate {
    
    private static Log logger = LogFactory.getLog(EmailTemplate.class);
    
    private String templateContent;
    
    public EmailTemplate(String templateContent) {
        if (StringUtils.isEmpty(templateContent)) {
            throw new ValidationException("The template content can't be null or empty!");
        }
        this.templateContent = templateContent;
    }
    
    public String evaluate(VelocityEngine velocityEngine, Map context) {
        VelocityContext vc = new VelocityContext(context);
        StringWriter emailMessageWriter = new StringWriter();
        try {
            velocityEngine.evaluate(vc,
                                    emailMessageWriter,
                                    "LOG",
                                    templateContent);
            return emailMessageWriter.toString();
        }
        catch (Exception e) {
            logger.error(e, e);
            throw new EmailTemplateException("Exception occurred while trying to generate the email content",
                                             e);
        }
    }
}
