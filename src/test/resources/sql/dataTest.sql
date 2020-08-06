INSERT INTO category (name, description) VALUES ('toy', 'an object for children to play with');
INSERT INTO category (name, description) VALUES ('toy', 'an object for children to play with');
INSERT INTO category (name, description) VALUES ('supda dupa toy', 'an object for children to play with');

INSERT INTO db_user (id, email, username, password, role) VALUES (1, 'email@gmail.com', 'superuser', null, 'ADMIN');
INSERT INTO db_user (id, email, username, password, role) VALUES (2, 'emai1l@gmail.com', 'superuser1', '$2a$10$cr2adEW5fYKp9WN5rA87weBFMNI4SqZiuNWJNpixmzKIOpFNcPRdO', 'CUSTOMER');

INSERT INTO item (id, item_code, name, description, price) VALUES (1, 'BH-1123', 'robot toy', 'Its robot toy for children from 3 years old', 115.2);
INSERT INTO item (id, item_code, name, description, price) VALUES (2, 'BH-111123', 'robo2t toy', 'Its robot toy for children from 3 years old', 115.221);
INSERT INTO item (id, item_code, name, description, price) VALUES (3, 'BH-212', 'boxing gloves', 'boxing gloves', 115.221);

INSERT INTO warehouse (id, address) VALUES (1, 'USA, New-York, Wall Street');
INSERT INTO warehouse (id, address) VALUES (2, 'Russia, Cheboksary, Zaovragom');

INSERT INTO shop (id, name, warehouse_id) VALUES (2, 'Super shop', 1);
INSERT INTO supplier (id, name) VALUES (1, 'Super retail group');



INSERT INTO db_order (id, is_confirmed, ordered_at, customer_id, shop_id) VALUES (2, false, null, 1, 2);
INSERT INTO db_order (id, is_confirmed, ordered_at, customer_id, shop_id) VALUES (3, true, '2020-08-03 15:56:50.601873', 2, 2);

INSERT INTO invoice (id, arrived_at, warehouse_id, supplier_id, is_confirmed) VALUES (6, '2000-11-11 11:11:11.000000', 1, 1, true);
INSERT INTO invoice (id, arrived_at, warehouse_id, supplier_id, is_confirmed) VALUES (7, '2000-11-11 11:11:11.000000', 1, 1, true);
INSERT INTO invoice (id, arrived_at, warehouse_id, supplier_id, is_confirmed) VALUES (8, '2000-11-11 11:11:11.000000', 1, 1, false);

INSERT INTO writeoff (id, time, warehouse_id, is_confirmed) VALUES (1, '2000-11-11 11:11:11.000000', 1, true);
INSERT INTO writeoff (id, time, warehouse_id, is_confirmed) VALUES (2, '2000-11-11 11:11:11.000000', 1, FALSE);


INSERT INTO item_category (item_id, category_id) VALUES (1, 1);
INSERT INTO item_category (item_id, category_id) VALUES (2, 1);
INSERT INTO item_category (item_id, category_id) VALUES (3, 1);


INSERT INTO item_invoice (item_id, invoice_id, quantity) VALUES (2, 6, 42);
INSERT INTO item_invoice (item_id, invoice_id, quantity) VALUES (1, 6, 1);
INSERT INTO item_invoice (item_id, invoice_id, quantity) VALUES (1, 7, 1);
INSERT INTO item_invoice (item_id, invoice_id, quantity) VALUES (2, 7, 1);
INSERT INTO item_invoice (item_id, invoice_id, quantity) VALUES (1, 8, 1);
INSERT INTO item_invoice (item_id, invoice_id, quantity) VALUES (2, 8, 1);


INSERT INTO item_order (item_id, order_id, quantity) VALUES (2, 2, 1);


INSERT INTO item_warehouse (item_id, warehouse_id, quantity) VALUES (1, 1, 1);
INSERT INTO item_warehouse (item_id, warehouse_id, quantity) VALUES (2, 1, 84);


INSERT INTO item_writeoff (item_id, writeoff_id, quantity) VALUES (1, 1, 1);
INSERT INTO item_writeoff (item_id, writeoff_id, quantity) VALUES (2, 1, 1);
INSERT INTO item_writeoff (item_id, writeoff_id, quantity) VALUES (1, 2, 1);
INSERT INTO item_writeoff (item_id, writeoff_id, quantity) VALUES (2, 2, 1);

