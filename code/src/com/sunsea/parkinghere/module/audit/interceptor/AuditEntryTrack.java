package com.sunsea.parkinghere.module.audit.interceptor;

import com.sunsea.parkinghere.module.audit.model.AuditEntry;

public class AuditEntryTrack {
    
    public static AuditEntryTrack create() {
        AuditEntryTrack result = new AuditEntryTrack();
        result.auditEntry = new AuditEntry();
        result.lastMills = System.currentTimeMillis();
        return result;
    }
    
    private AuditEntry auditEntry;
    
    private boolean matched = false;
    
    private long lastMills = -1l;
    
    public AuditEntry getAuditEntry() {
        return auditEntry;
    }
    
    public void setAuditEntry(AuditEntry auditEntry) {
        this.auditEntry = auditEntry;
    }
    
    public boolean isMatched() {
        return matched;
    }
    
    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    
    public long getLastMills() {
        return lastMills;
    }
    
    public void setLastMills(long lastMills) {
        this.lastMills = lastMills;
    }
    
}
