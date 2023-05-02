package com.gznznzjsn.inventoryservice.core.persistence.repository;

import com.gznznzjsn.inventoryservice.core.model.EmployeeRequirement;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface EmployeeRequirementRepository
        extends R2dbcRepository<EmployeeRequirement, UUID> {
}
