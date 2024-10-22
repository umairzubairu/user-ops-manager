package com.uzu.user.repositories;

import com.uzu.user.entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}
