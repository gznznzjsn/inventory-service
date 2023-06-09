--liquibase formatted sql

--changeset gznznzjsn:create-employee_requirements
create table employee_requirements
(
    employee_requirement_id uuid primary key,
    inventory_id            uuid not null references inventories on DELETE cascade,
    specialization          varchar(40) not null,
    equipment_name          varchar(40) not null
);
--rollback drop table employee_requirements;