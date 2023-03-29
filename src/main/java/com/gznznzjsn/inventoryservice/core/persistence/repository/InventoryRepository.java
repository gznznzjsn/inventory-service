package com.gznznzjsn.inventoryservice.core.persistence.repository;

import com.gznznzjsn.inventoryservice.core.model.Inventory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface InventoryRepository extends R2dbcRepository<Inventory, UUID> {
}
