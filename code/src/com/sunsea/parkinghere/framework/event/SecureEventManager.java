package com.sunsea.parkinghere.framework.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SecureEventManager {
    
    static class EventManagerHolder {
        static final SecureEventManager instance = new SecureEventManager();
    }
    
    public static SecureEventManager getInstance() {
        return EventManagerHolder.instance;
    }
    
    private List<SecureEventListener> listeners = new CopyOnWriteArrayList<SecureEventListener>();
    
    private SecureEventManager() {
    }
    
    public void registerListener(SecureEventListener listener) {
        listeners.add(listener);
    }
    
    public void dispatchEvent(SecureEventObject eventObject) {
        for (SecureEventListener listener : listeners) {
            try {
                listener.onEvent(eventObject);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
