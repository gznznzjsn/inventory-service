package com.gznznzjsn.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentAssignedEvent {

    private UUID employeeId;
    private String specialization;

}
