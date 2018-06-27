package com.sunsea.parkinghere.framework.email.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.IOUtils;

public class EmailTemplateManager {
    
    static class EmailTemplateManagerHolder {
        static EmailTemplateManager instance = new EmailTemplateManager();
    }
    
    public static EmailTemplateManager getInstance() {
        return EmailTemplateManagerHolder.instance;
    }
    
    private Map<String, EmailTemplate> repo = new HashMap<String, EmailTemplate>();
    
    private ReentrantLock lock = new ReentrantLock();
    
    public EmailTemplate getTemplate(String path) {
        EmailTemplate result = repo.get(path);
        if (result != null) {
            return result;
        }
        
        try {
            lock.lock();
            
            result = repo.get(path);
            if (result != null) {
                return result;
            }
            
            InputStream is = this.getClass()
                                 .getClassLoader()
                                 .getResourceAsStream(path);
            if (is == null) {
                return null;
            }
            
            try {
                String content = IOUtils.toString(is);
                result = new EmailTemplate(content);
                repo.put(path, result);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
            return result;
        }
        finally {
            lock.unlock();
        }
        
    }
    
}
