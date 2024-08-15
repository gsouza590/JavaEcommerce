CREATE TABLE countries (
    country_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE cities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country_id BIGINT,
    CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES countries(country_id)
);

CREATE TABLE roles (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE admins (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    image VARCHAR(255),
    UNIQUE(username, image(255))
);

CREATE TABLE customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    phone_number VARCHAR(255),
    address VARCHAR(255),
    city_id BIGINT,
    country VARCHAR(255),
    image MEDIUMBLOB,
    CONSTRAINT fk_city FOREIGN KEY (city_id) REFERENCES cities(id),
    UNIQUE(username, phone_number)
);

CREATE TABLE categories (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    is_deleted BOOLEAN NOT NULL,
    is_activated BOOLEAN NOT NULL
);

CREATE TABLE products (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    cost_price DECIMAL(19, 2) NOT NULL,
    sale_price DECIMAL(19, 2) NOT NULL,
    current_quantity INT NOT NULL,
    image MEDIUMBLOB,
    category_id BIGINT,
    is_deleted BOOLEAN NOT NULL,
    is_activated BOOLEAN NOT NULL,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(category_id),
    UNIQUE(name, image(255))
);

CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date DATE NOT NULL,
    delivery_date DATE,
    order_status VARCHAR(255),
    total_price DECIMAL(19, 2) NOT NULL,
    quantity INT NOT NULL,
    payment_method VARCHAR(255),
    is_accept BOOLEAN NOT NULL,
    customer_id BIGINT,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_detail (
    order_detail_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 1),
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(order_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE shopping_cart (
    shopping_cart_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    total_price DECIMAL(19, 2) NOT NULL,
    total_items INT NOT NULL,
    CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shopping_cart_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    unit_price DECIMAL(19, 2) NOT NULL,
    CONSTRAINT fk_cart FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(shopping_cart_id),
    CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE customers_roles (
    customer_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_customer_role FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_role_customer FOREIGN KEY (role_id) REFERENCES roles(role_id),
    PRIMARY KEY (customer_id, role_id)
);

CREATE TABLE admins_roles (
    admin_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_admin_role FOREIGN KEY (admin_id) REFERENCES admins(admin_id),
    CONSTRAINT fk_role_admin FOREIGN KEY (role_id) REFERENCES roles(role_id),
    PRIMARY KEY (admin_id, role_id)
);
