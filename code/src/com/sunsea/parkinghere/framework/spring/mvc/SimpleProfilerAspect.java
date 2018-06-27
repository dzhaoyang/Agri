package com.sunsea.parkinghere.framework.spring.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.util.StopWatch;

// @Aspect
public class SimpleProfilerAspect implements Ordered {
    
    private static Log logger = LogFactory.getLog(SimpleProfilerAspect.class);
    
    private int order;
    
    public int getOrder() {
        return this.order;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    // execution - for matching method execution join points, this is the
    // primary pointcut designator you will use when working with Spring AOP
    // pointcut matches the execution of any public method within the
    // com.cuethink.service package regardless of the return or parameter types.
    @Pointcut("execution(* com.google.code.std.service.*.*(..))")
    public void serviceOperation() {
    }
    
    @Around("serviceOperation()")
    public Object profile(ProceedingJoinPoint jp) throws Throwable {
        Object returnValue;
        StopWatch clock = new StopWatch(getClass().getName());
        try {
            clock.start(jp.toShortString());
            returnValue = jp.proceed();
        }
        finally {
            clock.stop();
            
            if (logger.isInfoEnabled()) {
                logger.info("**** SimpleProfilerAspect.profile() invoked ****");
                logger.info(String.format("Proxy = %s", jp.getThis().getClass()));
                logger.info(String.format("Target = %s", jp.getTarget()
                                                           .getClass()));
                logger.info(String.format("Signature = %s", jp.getSignature()));
                logger.info(String.format("Info = %s", jp.toString()));
                logger.info(String.format("Execution time = %s",
                                          clock.prettyPrint()));
            }
        }
        return returnValue;
    }
}
