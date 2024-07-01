
INSERT INTO roles (role_id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_CUSTOMER')
ON DUPLICATE KEY UPDATE name=VALUES(name);


INSERT INTO admins
(admin_id, first_name, last_name, username, password, image)
VALUES
(1, 'Gabriel', 'Souza', 'gdomiciano.adv@gmail.com', '$2a$10$nyQADclo3Qpi7H85CgGUr.kQNCIMS7NBGuivsqyluO8dINOmNlv8S', NULL);


INSERT INTO admins_roles (admin_id, role_id) VALUES (1, 1);


INSERT INTO customers  (customer_id, first_name, last_name, username, password, phone_number, address, city_id, country, image)
VALUES (1, 'Gabriel', 'Souza', 'gdomiciano.adv@gmail.com', '$2a$10$nyQADclo3Qpi7H85CgGUr.kQNCIMS7NBGuivsqyluO8dINOmNlv8S', '', '', NULL, '', NULL);

INSERT INTO customers_roles  (customer_id, role_id)VALUES (1, 2);
