--liquibase formatted sql

--changeset gznznzjsn:create-requirements
create table requirements
(
    requirement_id uuid primary key,
    inventory_id            uuid not null references inventories on DELETE cascade,
    specialization          varchar(40) not null,
    equipment_name          varchar(40) not null
);
--rollback drop table requirements;