package com.sunsea.parkinghere.framework.edm.akka;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorSystem;
import akka.util.Duration;

import com.sunsea.parkinghere.framework.edm.SecurityEdmCenter;
import com.sunsea.parkinghere.framework.edm.SecurityEdmCenterProvider;

public class DefaultSecurityEdmCenterProvider implements
                                             SecurityEdmCenterProvider {
    
    private DefaultSecurityEdmCenter defaultInstance = new DefaultSecurityEdmCenter();
    
    private ConcurrentHashMap<String, SecurityEdmCenter> instances = new ConcurrentHashMap<String, SecurityEdmCenter>();
    
    public SecurityEdmCenter getInstance() {
        return defaultInstance;
    }
    
    public SecurityEdmCenter getInstance(String name) {
        SecurityEdmCenter result = instances.get(name);
        if (result != null) {
            return result;
        }
        
        instances.putIfAbsent(name, new DefaultSecurityEdmCenter(name));
        return instances.get(name);
    }
    
    public void shutdown() {
        defaultInstance.acceptActorSystemVisitor(new ActorSystemVisitor() {
            
            public void visit(ActorSystem system) {
                try {
                    system.shutdown();
                    system.awaitTermination(Duration.create(15,
                                                            TimeUnit.SECONDS));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        for (Iterator<SecurityEdmCenter> iterator = instances.values()
                                                             .iterator(); iterator.hasNext();) {
            ((DefaultSecurityEdmCenter) iterator.next()).acceptActorSystemVisitor(new ActorSystemVisitor() {
                
                public void visit(ActorSystem system) {
                    try {
                        system.shutdown();
                        system.awaitTermination(Duration.create(15,
                                                                TimeUnit.SECONDS));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
