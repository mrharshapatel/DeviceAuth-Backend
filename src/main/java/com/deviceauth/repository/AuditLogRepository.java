package com.deviceauth.repository;
import com.deviceauth.entity.AuditLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditLogRepository extends JpaRepository<AuditLog,Long> { List<AuditLog> findTop100ByOrderByTimestampDesc(); }
