package com.sunsea.parkinghere.framework.edm.akka;

import akka.actor.ActorSystem;

public interface ActorSystemVisitor {
    
    void visit(ActorSystem system);
    
}
