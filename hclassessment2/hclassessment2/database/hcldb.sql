CREATE DATABASE IF NOT EXISTS hcldb;
USE hcldb;

CREATE TABLE IF NOT EXISTS customer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS cart (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cart_customer (customer_id),
    CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cartItem` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cartitem_cart FOREIGN KEY (cart_id) REFERENCES cart (id),
    CONSTRAINT fk_cartitem_product FOREIGN KEY (product_id) REFERENCES product (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `customerOrder` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `orderItem` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_orderitem_order FOREIGN KEY (order_id) REFERENCES `customerOrder` (id),
    CONSTRAINT fk_orderitem_product FOREIGN KEY (product_id) REFERENCES product (id)
) ENGINE = InnoDB;

INSERT IGNORE INTO customer (id, name, email) VALUES
    (1, 'Rahul Sharma', 'rahul@example.com');

INSERT IGNORE INTO product (id, name, price) VALUES
    (1, 'Wireless Mouse', 699.00),
    (2, 'Mechanical Keyboard', 2499.00),
    (3, 'USB-C Cable', 299.00);

INSERT IGNORE INTO cart (id, customer_id) VALUES
    (1, 1);
