USE workflow;

CREATE TABLE IF NOT EXISTS positions
(
    id binary(16) NOT NULL,
    name varchar(64) NOT NULL,
    level int NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS employees
(
    id binary(16) NOT NULL,
    name varchar(64) NOT NULL,
    position_id binary(16),
    PRIMARY KEY (id),
    FOREIGN KEY (position_id) REFERENCES workflow.positions(id)
);