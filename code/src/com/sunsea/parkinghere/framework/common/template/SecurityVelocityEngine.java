package com.sunsea.parkinghere.framework.common.template;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

public class SecurityVelocityEngine extends VelocityEngine {
    
    public SecurityVelocityEngine() {
        super();
    }
    
    public void init() {
        this.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                         "org.apache.velocity.runtime.log.Log4JLogChute");
        this.setProperty("runtime.log.logsystem.log4j.logger",
                         SecurityVelocityEngine.class.getName());
        super.init();
    }
}
