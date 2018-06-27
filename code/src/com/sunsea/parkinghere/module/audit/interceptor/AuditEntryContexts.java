package com.sunsea.parkinghere.module.audit.interceptor;

public class AuditEntryContexts {
    
    private static final ThreadLocal<AuditEntryTrack> auditEntryTrackHolder = new ThreadLocal<AuditEntryTrack>();
    
    static AuditEntryTrack getCurrent() {
        return auditEntryTrackHolder.get();
    }
    
    static void setCurrent(AuditEntryTrack value) {
        auditEntryTrackHolder.set(value);
    }
    
    static void clear() {
        auditEntryTrackHolder.set(null);
    }
    
}
