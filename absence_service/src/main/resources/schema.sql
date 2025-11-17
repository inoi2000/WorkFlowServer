CREATE TABLE IF NOT EXISTS policies
(
    id BINARY(16) NOT NULL,
    type VARCHAR(64) UNIQUE NOT NULL,
    max_duration_days INTEGER NOT NULL,
    requires_approval BOOLEAN NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS policies_positions
(
    policy_id BINARY(16) NOT NULL,
    position_id BINARY(16) NOT NULL,
    PRIMARY KEY (policy_id, position_id)
    );

CREATE TABLE IF NOT EXISTS absences
(
    id BINARY(16) NOT NULL,
    status VARCHAR(64) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    place VARCHAR(128),
    employee_id BINARY(16) NOT NULL,
    created_by_id BINARY(16) NOT NULL,
    policy_id BINARY(16) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (policy_id) REFERENCES policies(id)
    );

CREATE TABLE IF NOT EXISTS approvals
(
    id BINARY(16) NOT NULL,
    absence_id BINARY(16) NOT NULL,
    approver_id BINARY(16) NOT NULL,
    description VARCHAR(256),
    PRIMARY KEY (id),
    FOREIGN KEY (absence_id) REFERENCES absences(id)
    );