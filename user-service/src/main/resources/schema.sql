CREATE TABLE IF NOT EXISTS users (
    id binary(16) NOT NULL,
    username varchar(16) UNIQUE NOT NULL,
    password varchar(64) NOT NULL,
    email varchar(64) NOT NULL,
    role varchar(24),
    PRIMARY KEY (id)
);