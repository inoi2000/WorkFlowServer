CREATE TABLE IF NOT EXISTS statements
(
    id BINARY(16) NOT NULL,
    logist_id BINARY(16) NOT NULL,		-- Id работника
    data VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS cars
(
    id BINARY(16) NOT NULL,
    brand VARCHAR(100) NOT NULL,           -- Марка машины
    model VARCHAR(100) NOT NULL,           -- Модель машины
    license_plate VARCHAR(20) UNIQUE NOT NULL, -- Госномер
    vin VARCHAR(17) UNIQUE,                -- VIN номер
    year INTEGER,                          -- Год выпуска
    color VARCHAR(50),                     -- Цвет
    odometer DOUBLE,                       -- Одометр
    status ENUM('ACTIVE', 'MAINTENANCE', 'INACTIVE') NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS journeys
(
    id BINARY(16) NOT NULL,
    car_id BINARY(16) NOT NULL,				-- Id машины
    statement_id BINARY(16) NOT NULL UNIQUE,-- Id заявки
    driver_id BINARY(16) NOT NULL,			-- Id работника
    status ENUM('NEW', 'CONFIRMED', 'STARTED', 'FINISHED', 'CANCELED') NOT NULL,
    start_odometer DOUBLE,
    end_odometer DOUBLE,
    estimated_duration_minutes INT NOT NULL,
    created_at DATETIME NOT NULL,
    confirmed_at DATETIME,
    started_at DATETIME,
    finished_at DATETIME,
    canceled_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (car_id) REFERENCES workflow.cars(id),
    FOREIGN KEY (statement_id) REFERENCES workflow.statements(id)
    );

CREATE TABLE IF NOT EXISTS fuellings
(
    id BINARY(16) NOT NULL,
    car_id BINARY(16) NOT NULL,				-- Id машины
    driver_id BINARY(16) NOT NULL,			-- Id работника
    operator_id BINARY(16) NOT NULL,		-- Id работника
    volume DOUBLE NOT NULL, 			    -- обьем топлива
    created_at DATETIME NOT NULL,			-- время заправки
    PRIMARY KEY (id),
    FOREIGN KEY (car_id) REFERENCES workflow.cars(id)
    );


