CREATE TABLE IF NOT EXISTS announcements
(
    id BINARY(16) NOT NULL,
    title VARCHAR(128) NOT NULL,
    created_at DATETIME NOT NULL,
    content VARCHAR(512) NOT NULL,
    img_url VARCHAR(128),
    PRIMARY KEY (id)
);