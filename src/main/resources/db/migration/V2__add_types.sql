insert into types_of_products (id, type_name, created_at)
values  (1,'Канапе', current_timestamp),
        (2,'Тарталетки', current_timestamp),
        (3,'Сэндвичи, брускетты', current_timestamp),
        (4,'Пирожки', current_timestamp),
        (5,'Мини-бургеры', current_timestamp),
        (6,'Самса', current_timestamp),
        (7,'Пицца', current_timestamp),
        (8,'Десерты', current_timestamp),
        (9,'Рулетики', current_timestamp),
        (10,'Канапе', current_timestamp),
        (11,'Холодные закуски', current_timestamp),
        (12,'Горячие закуски', current_timestamp),
        (13,'Нарезки', current_timestamp),
        (14,'Наборы сыров', current_timestamp),
        (15,'Набор колбас', current_timestamp),
        (16,'Салаты', current_timestamp),
        (17,'Овощи и фрукты', current_timestamp),
        (18,'Шашлык', current_timestamp),
        (19,'Блины', current_timestamp),
        (20,'Готовые сеты', current_timestamp),
        (21,'Напитки', current_timestamp),
        (22,'Посуда', current_timestamp),
        (23,'Столы', current_timestamp),
        (24,'Скатерти', current_timestamp);
-- =======================
ALTER SEQUENCE prod_type_seq RESTART WITH 24;

