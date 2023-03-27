--liquibase formatted sql

--changeset gznznzjsn:create-equipment
create table equipment
(
    equipment_id   uuid primary key,
    inventory_id   uuid        not null references inventories on DELETE cascade,
    equipment_name varchar(40) not null,
    owner_id       uuid
);
--rollback drop table equipment;