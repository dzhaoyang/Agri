package com.sunsea.parkinghere.framework.edm.akka;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.actors.threadpool.Arrays;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RandomRouter;
import akka.routing.RoundRobinRouter;
import akka.routing.SmallestMailboxRouter;

import com.sunsea.parkinghere.framework.edm.SecurityEdmCenter;
import com.sunsea.parkinghere.framework.edm.SecurityEdmException;
import com.sunsea.parkinghere.framework.edm.SecurityEventAroundListener;
import com.sunsea.parkinghere.framework.edm.SecurityEventListener;
import com.sunsea.parkinghere.framework.edm.SecurityEventObject;
import com.sunsea.parkinghere.framework.edm.SecurityMessage;
import com.sunsea.parkinghere.framework.edm.SecurityMessageHandler;
import com.sunsea.parkinghere.framework.edm.SecurityMessageQueue;
import com.sunsea.parkinghere.framework.edm.SecurityMessageStrategy;
import com.sunsea.parkinghere.framework.edm.SecurityNamedEventListener;
import com.sunsea.parkinghere.framework.edm.SecurityNamedEventObject;

/**
 */
public class DefaultSecurityEdmCenter implements SecurityEdmCenter {
    
    public static final String DEFAULT_CENTER_NAME = "security";
    
    private static Log logger = LogFactory.getLog(DefaultSecurityEdmCenter.class);
    
    private List<SecurityEventListener> listeners = new CopyOnWriteArrayList<SecurityEventListener>();
    
    private ConcurrentHashMap<String, SecurityNamedEventListener> namedListeners = new ConcurrentHashMap<String, SecurityNamedEventListener>();
    
    public ActorSystem system;
    
    public DefaultSecurityEdmCenter() {
        // TODO delegate this to akka actor system creator which support
        // customized configuration.
        system = ActorSystem.create(DEFAULT_CENTER_NAME);
    }
    
    public DefaultSecurityEdmCenter(String name) {
        if (StringUtils.isEmpty(name)) {
            system = ActorSystem.create(DEFAULT_CENTER_NAME);
            return;
        }
        system = ActorSystem.create(name);
    }
    
    public String getName() {
        return system.name();
    }
    
    public void registerEventListener(SecurityEventListener listener) {
        if (listener == null) {
            return;
        }
        listeners.add(listener);
    }
    
    public void unregisterEventListener(SecurityEventListener listener) {
        if (listener == null) {
            return;
        }
        listeners.remove(listener);
    }
    
    public void registerEventListener(String name,
                                      SecurityNamedEventListener listener) {
        if (name == null) {
            return;
        }
        if (listener == null) {
            throw new IllegalArgumentException("The listener must be specified!");
        }
        
        SecurityNamedEventListener result = namedListeners.putIfAbsent(name,
                                                                       listener);
        if (result != null) {
            throw new IllegalStateException(String.format("The CueThinkNamedEventListener associated with the name '%s' found!",
                                                          name));
        }
    }
    
    public SecurityNamedEventListener unregisterEventListener(String name) {
        if (name == null) {
            return null;
        }
        return namedListeners.remove(name);
    }
    
    public void fireEvent(SecurityEventObject eventObject) {
        if (null == eventObject) {
            return;
        }
        
        if (eventObject instanceof SecurityNamedEventObject) {
            SecurityNamedEventObject namedEventObject = (SecurityNamedEventObject) eventObject;
            SecurityNamedEventListener namedEventListener = namedListeners.get(namedEventObject.getName());
            
            if (namedEventListener != null) {
                try {
                    namedEventListener.onFire(namedEventObject);
                }
                catch (Exception e) {
                    logger.error(e, e);
                }
                return;
            }
        }
        
        for (SecurityEventListener listener : listeners) {
            try {
                if (listener.isSupported(eventObject)) {
                    if (listener instanceof SecurityEventAroundListener) {
                        ((SecurityEventAroundListener) listener).beforeFire(eventObject);
                    }
                    
                    listener.onFire(eventObject);
                    
                    if (listener instanceof SecurityEventAroundListener) {
                        ((SecurityEventAroundListener) listener).afterFire(eventObject);
                    }
                }
            }
            catch (Exception e) {
                logger.error(e, e);
            }
        }
        
    }
    
    public void acceptActorSystemVisitor(ActorSystemVisitor visitor) {
        visitor.visit(system);
    }
    
    public void registerMessageHandler(final String path,
                                       final SecurityMessageHandler handler) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("The path must be specified!");
        }
        if (handler == null) {
            throw new IllegalArgumentException("The handler must be specified!");
        }
        
        acceptActorSystemVisitor(new ActorSystemVisitor() {
            
            public void visit(ActorSystem system) {
                try {
                    System.out.println(system.actorOf(new Props(new UntypedActorFactory() {
                                                          
                                                          public Actor create() {
                                                              return new AkkaUntypedActorAdapter(handler);
                                                          }
                                                          
                                                      }),
                                                      path) + " created");
                    ;
                }
                catch (Exception e) {
                    throw new SecurityEdmException("Register failed!" + e.getMessage(),
                                                   e);
                }
            }
            
        });
    }
    
    public void registerMessageHandler(final String path,
                                       final SecurityMessageHandler handler,
                                       final SecurityMessageStrategy strategy,
                                       final int maxSize) {
        if (strategy == null) {
            throw new IllegalArgumentException("The cuethink message routing strategy must be specified!");
        }
        if (maxSize < 1) {
            throw new IllegalArgumentException("The max size of handler must be greater than zero!");
        }
        if (maxSize == 0) {
            logger.warn("The max size of handlers equals to 1, the strategy will be ignored!");
            registerMessageHandler(path, handler);
            return;
        }
        
        final ActorRef[] actorRefs = new ActorRef[maxSize];
        final int[] indexes = new int[] { 0 };
        for (int i = 0; i < maxSize; i++) {
            indexes[0] = i;
            actorRefs[i] = system.actorOf(new Props(new UntypedActorFactory() {
                
                public Actor create() {
                    return new AkkaUntypedActorAdapter(handler);
                }
                
            }));
            System.out.println(actorRefs[i] + " created!");
        }
        
        acceptActorSystemVisitor(new ActorSystemVisitor() {
            
            public void visit(ActorSystem system) {
                try {
                    if (strategy == SecurityMessageStrategy.RoundRobinRouting) {
                        System.out.println(system.actorOf(new Props(AkkaUntypedActorAdapter.class).withRouter(RoundRobinRouter.create(Arrays.asList(actorRefs))),
                                                          path) + " created");
                    }
                    else if (strategy == SecurityMessageStrategy.RandomRouting) {
                        System.out.println(system.actorOf(new Props(AkkaUntypedActorAdapter.class).withRouter(RandomRouter.create(Arrays.asList(actorRefs))),
                                                          path) + " created");
                    }
                    else if (strategy == SecurityMessageStrategy.SmallestQueueRouting) {
                        System.out.println(system.actorOf(new Props(AkkaUntypedActorAdapter.class).withRouter(SmallestMailboxRouter.create(Arrays.asList(actorRefs))),
                                                          path) + " created");
                    }
                    else {
                        throw new SecurityEdmException("Unsupported " + strategy);
                    }
                }
                catch (SecurityEdmException e) {
                    throw e;
                }
                catch (Exception e) {
                    throw new SecurityEdmException("Register failed!" + e.getMessage(),
                                                   e);
                }
            }
            
        });
    }
    
    public SecurityMessageHandler unregisterMessageHandler(String path) {
        throw new UnsupportedOperationException();
    }
    
    public void sendMessage(SecurityMessage message) {
        String path = "/user";
        if (message.getPath().startsWith("akka:")) {
            system.actorFor(message.getPath()).tell(message);
            return;
        }
        if (message.getPath().startsWith("/")) {
            path += message.getPath();
        }
        else {
            path += "/" + message.getPath();
        }
        system.actorFor(path).tell(message);
    }
    
    public void sendMessage(String fullPath, SecurityMessage message) {
        system.actorFor(fullPath).tell(message);
    }
    
    public SecurityMessageQueue getMessageQueue(String path) {
        if (path.startsWith("akka:")) {
            return new DefaultSecurityMessageQueue();
        }
        else {
            path = "/user/" + path;
            return new DefaultSecurityMessageQueue();
        }
    }
    
    public class AkkaUntypedActorAdapter extends UntypedActor {
        
        private LoggingAdapter logger = Logging.getLogger(getContext().system(),
                                                          this);
        
        private SecurityMessageHandler handler;
        
        public AkkaUntypedActorAdapter(SecurityMessageHandler handler) {
            this.handler = handler;
        }
        
        public void onReceive(Object message) throws Exception {
            if (message instanceof SecurityMessage) {
                System.out.println(String.format("%s matched!",
                                                 getSelf().toString()));
                handler.handle((SecurityMessage) message);
            }
            else {
                unhandled(message);
            }
        }
    }
}
