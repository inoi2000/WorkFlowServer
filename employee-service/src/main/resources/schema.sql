CREATE TABLE IF NOT EXISTS departments
(
    id BINARY(16) NOT NULL,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS positions
(
    id BINARY(16) NOT NULL,
    name VARCHAR(64) NOT NULL,
    level INT NOT NULL,
    requires_special_documents BOOLEAN NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS employees
(
    id BINARY(16) NOT NULL,
    name VARCHAR(64) NOT NULL,
    phone VARCHAR(12) NOT NULL,
    email VARCHAR(32) NOT NULL,
    position_id BINARY(16),
    department_id BINARY(16),
    PRIMARY KEY (id),
    FOREIGN KEY (position_id) REFERENCES workflow.positions(id),
    FOREIGN KEY (department_id) REFERENCES workflow.departments(id)
    );

CREATE TABLE IF NOT EXISTS accesses_data
(
    id BINARY(16) NOT NULL,
    text VARCHAR(1024) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS accesses
(
    id BINARY(16) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    type ENUM('ONETIME', 'TEMPORARY', 'PERMANENT') NOT NULL,
    valid_until DATETIME,
    issuer_id BINARY(16) NOT NULL,
    holder_id BINARY(16) NOT NULL,
    data_id BINARY(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (issuer_id) REFERENCES workflow.employees(id),
    FOREIGN KEY (holder_id) REFERENCES workflow.employees(id),
    FOREIGN KEY (data_id) REFERENCES workflow.accesses_data(id)
    );

CREATE TABLE IF NOT EXISTS instructions_data
(
    id BINARY(16) NOT NULL,
    text VARCHAR(1024) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS instructions
(
    id BINARY(16) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    valid_until DATETIME,
    instructor_id BINARY(16) NOT NULL,
    data_id BINARY(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (instructor_id) REFERENCES workflow.employees(id),
    FOREIGN KEY (data_id) REFERENCES workflow.instructions_data(id)
    );

CREATE TABLE IF NOT EXISTS employee_instruction_statuses
(
    employee_id BINARY(16) NOT NULL,
    instruction_id BINARY(16) NOT NULL,
    is_confirmed BOOLEAN,
    confirmed_at DATETIME,
    PRIMARY KEY (employee_id, instruction_id),
    FOREIGN KEY (employee_id) REFERENCES workflow.employees(id),
    FOREIGN KEY (instruction_id) REFERENCES workflow.instructions(id)
    );
CREATE TABLE IF NOT EXISTS instruction_confirmations
(
    id BINARY(16) NOT NULL,
    employee_id BINARY(16) NOT NULL,
    instruction_id BINARY(16) NOT NULL,
    is_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    confirmed_at DATETIME NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_employee_instruction (employee_id, instruction_id),
    FOREIGN KEY (employee_id) REFERENCES workflow.employees(id),
    FOREIGN KEY (instruction_id) REFERENCES workflow.instructions(id)
    );