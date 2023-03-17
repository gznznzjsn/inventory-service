--liquibase formatted sql


--changeset gznznzjsn:fulfill-consumable-types
insert into consumable_types (name, cost)
values ('tire', 1000),
       ('pin', 100),
       ('red paint', 800),
       ('black paint', 900),
       ('headlight', 2000);

--changeset gznznzjsn:fulfill-consumables
insert into consumables (consumable_type_id, available_quantity)
values (1, 20),
       (2, 1000),
       (3, 140),
       (4, 90),
       (5, 15);



