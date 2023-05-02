package com.gznznzjsn.inventoryservice.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private UUID id;
    private Glossary glossary;
    private String name;
    private Specialization specialization;

}
