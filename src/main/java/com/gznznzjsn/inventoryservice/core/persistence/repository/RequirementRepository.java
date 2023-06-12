package com.gznznzjsn.inventoryservice.core.persistence.repository;

import com.gznznzjsn.inventoryservice.core.model.Requirement;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface RequirementRepository
        extends R2dbcRepository<Requirement, UUID> {
}
