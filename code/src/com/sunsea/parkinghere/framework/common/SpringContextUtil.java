package com.sunsea.parkinghere.framework.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * 获取spring上下文
 * @author ylr
 *
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext; // Spring应用上下文环境
	
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		SpringContextUtil.applicationContext = arg0;
	}
	
	public static ApplicationContext getApplicationContext() {
         return applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
        return (T)applicationContext.getBean(name);
	}

}
