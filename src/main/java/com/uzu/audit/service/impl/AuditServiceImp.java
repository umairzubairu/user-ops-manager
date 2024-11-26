package com.uzu.audit.service.impl;

import com.uzu.audit.service.AuditService;
import com.uzu.audit.entity.Audit;
import com.uzu.audit.repository.AuditRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuditServiceImp implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public void logAudit(String entityName, Long entityId, String action, String changedBy) {
        Audit audit = Audit.builder().entityId(entityId).entityName(entityName).action(action)
                .changedBy(changedBy).changedAt(LocalDateTime.now()).build();
        auditRepository.save(audit);
    }

    public void logAudits(List<Audit> audits) {
        auditRepository.saveAll(audits);
    }
}
