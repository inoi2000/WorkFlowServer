CREATE TABLE IF NOT EXISTS file_keys (
    id BINARY(16) NOT NULL,
    file_key VARCHAR(42) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS announcements
(
    id BINARY(16) NOT NULL,
    title VARCHAR(128) NOT NULL,
    created_at DATETIME NOT NULL,
    content VARCHAR(512) NOT NULL,
    file_key_id BINARY(16),
    PRIMARY KEY (id),
    FOREIGN KEY (file_key_id) REFERENCES file_keys(id)
);