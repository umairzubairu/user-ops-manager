package com.uzu.audit.service;


import com.uzu.audit.entity.Audit;
import java.util.List;

public interface AuditService {

    void logAudit(String entityName, Long entityId, String action, String changedBy);

    void logAudits(List<Audit> audits);

}
