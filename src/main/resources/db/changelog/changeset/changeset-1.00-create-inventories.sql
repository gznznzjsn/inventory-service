--liquibase formatted sql

--changeset gznznzjsn:create-inventories
create table inventories
(
    inventory_id uuid primary key
);
--rollback drop table inventories;