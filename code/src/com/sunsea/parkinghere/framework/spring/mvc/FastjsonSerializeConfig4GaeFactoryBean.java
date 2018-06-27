package com.sunsea.parkinghere.framework.spring.mvc;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.serializer.SerializeConfig;

public class FastjsonSerializeConfig4GaeFactoryBean implements
                                                   FactoryBean<SerializeConfig>,
                                                   InitializingBean {
    
    private SerializeConfig serializeConfig = new SerializeConfig();
    
    public SerializeConfig getObject() throws Exception {
        return serializeConfig;
    }
    
    public Class<SerializeConfig> getObjectType() {
        return SerializeConfig.class;
    }
    
    public boolean isSingleton() {
        return true;
    }
    
    public void afterPropertiesSet() throws Exception {
    }
    
}
