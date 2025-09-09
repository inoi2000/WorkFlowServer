CREATE TABLE IF NOT EXISTS absences
(
    id BINARY(16) NOT NULL,
    type VARCHAR(64) NOT NULL,
    status VARCHAR(64) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    place VARCHAR(128),
    is_approval BOOLEAN NOT NULL,
    employee_id BINARY(16) NOT NULL,
    PRIMARY KEY (id)
);