package com.example.ecommerce.Audit;
import org.springframework.data.repository.CrudRepository;

public interface AuditRepository extends CrudRepository<AuditModel, Long> {
    
}
