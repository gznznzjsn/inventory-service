--liquibase formatted sql

--changeset gznznzjsn:rename-requirements
ALTER TABLE IF EXISTS employee_requirements
    RENAME TO requirements;
--rollback ALTER TABLE IF EXISTS requirements RENAME TO employee_requirements;