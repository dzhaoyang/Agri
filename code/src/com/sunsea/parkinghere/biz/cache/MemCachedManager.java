package com.sunsea.parkinghere.biz.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemCachedManager {
    
    static class MemCachedManagerHolder {
        static final MemCachedManager instance = new MemCachedManager();
    }
    
    public static MemCachedManager getInstance() {
        return MemCachedManagerHolder.instance;
    }
    
    public Map<Class, Object> cachedEntities = new ConcurrentHashMap<Class, Object>();
    
    public void put(Class key, Object value) {
        cachedEntities.put(key, value);
    }
    
    public Object get(Class key) {
        return cachedEntities.get(key);
    }
    
    public void clear() {
        cachedEntities.clear();
    }
    
}
