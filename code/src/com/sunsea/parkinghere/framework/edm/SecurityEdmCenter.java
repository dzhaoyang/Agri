package com.sunsea.parkinghere.framework.edm;

/**
 * Edm = > Event driven message
 */
public interface SecurityEdmCenter {
    
    public void registerEventListener(SecurityEventListener listener);
    
    public void unregisterEventListener(SecurityEventListener listener);
    
    /**
     * Only one listener supported for one name. The implementation must make
     * sure Fail-fast supported.
     * <p/>
     * If the client likes to support multi-listeners for one name, the client
     * needs to pass one composite listener to this method.
     */
    public void registerEventListener(String name,
                                      SecurityNamedEventListener listener);
    
    public SecurityNamedEventListener unregisterEventListener(String name);
    
    public void fireEvent(SecurityEventObject eventObject);
    
    public void registerMessageHandler(String path,
                                       SecurityMessageHandler handler);
    
    public void registerMessageHandler(String path,
                                       SecurityMessageHandler handler,
                                       SecurityMessageStrategy strategy,
                                       int maxSize);
    
    public SecurityMessageHandler unregisterMessageHandler(String path);
    
    public void sendMessage(SecurityMessage message);
    
    public void sendMessage(String fullPath, SecurityMessage message);
    
    public SecurityMessageQueue getMessageQueue(String path);
    
}
