CREATE TABLE IF NOT EXISTS wastes
(
    id BINARY(16) NOT NULL,
    name VARCHAR(100) NOT NULL,
    is_recycle BOOLEAN NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS storages
(
    id BINARY(16) NOT NULL,
    name VARCHAR(100) NOT NULL,
    total_volume DOUBLE NOT NULL,
    current_volume DOUBLE NOT NULL,
    current_waste_id BINARY(16),
    PRIMARY KEY (id),
    FOREIGN KEY (current_waste_id) REFERENCES wastes(id)
    );

CREATE TABLE IF NOT EXISTS allowed_storages_wastes
(
    storage_id BINARY(16) NOT NULL,
    waste_id BINARY(16) NOT NULL,
    PRIMARY KEY (storage_id, waste_id),
    FOREIGN KEY (storage_id) REFERENCES storages(id),
    FOREIGN KEY (waste_id) REFERENCES wastes(id)
    );

CREATE TABLE IF NOT EXISTS storage_events
(
    id BINARY(16) NOT NULL,
    action_type ENUM('POURING', 'DRAINING') NOT NULL,
    volume DOUBLE NOT NULL,
    storage_id BINARY(16) NOT NULL,
    waste_id BINARY(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (storage_id) REFERENCES storages(id),
    FOREIGN KEY (waste_id) REFERENCES wastes(id)
    );
