USE workflow;

CREATE TABLE IF NOT EXISTS positions
(
    id BINARY(16) NOT NULL,
    name VARCHAR(64) NOT NULL,
    level INT NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS employees
(
    id BINARY(16) NOT NULL,
    name VARCHAR(64) NOT NULL,
    position_id BINARY(16),
    PRIMARY KEY (id),
    FOREIGN KEY (position_id) REFERENCES workflow.positions(id)
);