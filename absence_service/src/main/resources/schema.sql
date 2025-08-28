CREATE TABLE IF NOT EXISTS absences
(
    id binary(16) NOT NULL,
    type varchar(64) NOT NULL,
    status varchar(64) NOT NULL,
    start date NOT NULL,
    end date NOT NULL,
    place varchar(128),
    is_approval boolean NOT NULL,
    employee_id binary(16) NOT NULL,
    PRIMARY KEY (id)
);