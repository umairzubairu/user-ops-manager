package com.uzu.user.services;


import com.uzu.user.entities.Audit;
import java.util.List;

public interface AuditService {

    void logAudit(String entityName, Long entityId, String action, String changedBy);

    void logAudits(List<Audit> audits);

}
