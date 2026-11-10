CREATE TABLE IF NOT EXISTS products
(
    id BINARY(16) NOT NULL,
    name VARCHAR(100) NOT NULL,
    density DOUBLE NOT NULL,
    status VARCHAR(32) NOT NULL,
    type VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS storages
(
    id BINARY(16) NOT NULL,
    name VARCHAR(100) NOT NULL,
    total_volume DOUBLE NOT NULL,
    current_volume DOUBLE NOT NULL,
    current_product_id BINARY(16),
    allowed_mixing BOOLEAN NOT NULL,
    output_product_id BINARY(16),
    sector VARCHAR(32) NOT NULL,
    placement VARCHAR(32) NOT NULL,
    location VARCHAR(32) NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (current_product_id) REFERENCES products(id),
    FOREIGN KEY (output_product_id) REFERENCES products(id)
    );

CREATE TABLE IF NOT EXISTS allowed_storages_products
(
    storage_id BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    PRIMARY KEY (storage_id, product_id),
    FOREIGN KEY (storage_id) REFERENCES storages(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
    );

CREATE TABLE IF NOT EXISTS storage_events
(
    id BINARY(16) NOT NULL,
    action_type ENUM('POURING', 'DRAINING', 'CHECK') NOT NULL,
    volume DOUBLE NOT NULL,
    storage_id BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    employee_id BINARY(16) NOT NULL,
    created_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (storage_id) REFERENCES storages(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
    );
