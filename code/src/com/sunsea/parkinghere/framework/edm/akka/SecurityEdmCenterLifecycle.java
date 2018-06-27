package com.sunsea.parkinghere.framework.edm.akka;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.framework.edm.SecurityEdmCenterProviderFactory;
import com.sunsea.parkinghere.framework.edm.SecurityMessageHandler;

@Service
public class SecurityEdmCenterLifecycle implements
                                       ApplicationContextAware,
                                       ApplicationListener,
                                       DisposableBean {
    
    private static final ConcurrentHashMap<String, SecurityMessageHandler> repos = new ConcurrentHashMap<String, SecurityMessageHandler>();
    
    public static void registerMessageHandler(String path,
                                              SecurityMessageHandler handler) {
        repos.put(path, handler);
    }
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }
    
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextClosedEvent) {
            destroy();
        }
        if (event instanceof ContextRefreshedEvent) {
            for (Iterator<Entry<String, SecurityMessageHandler>> iterator = repos.entrySet()
                                                                                 .iterator(); iterator.hasNext();) {
                Entry<String, SecurityMessageHandler> entry = iterator.next();
                SecurityEdmCenterProviderFactory.getProvider()
                                                .getInstance()
                                                .registerMessageHandler(entry.getKey(),
                                                                        entry.getValue());
            }
            
            repos.clear();
        }
    }
    
    public void destroy() {
        ((DefaultSecurityEdmCenterProvider) SecurityEdmCenterProviderFactory.getProvider()).shutdown();
    }
    
}
