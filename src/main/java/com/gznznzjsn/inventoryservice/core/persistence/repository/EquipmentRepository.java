package com.gznznzjsn.inventoryservice.core.persistence.repository;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface EquipmentRepository extends R2dbcRepository<Equipment, UUID> {
}
