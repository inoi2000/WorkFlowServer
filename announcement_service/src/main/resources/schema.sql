CREATE TABLE IF NOT EXISTS announcements
(
    id binary(16) NOT NULL,
    title varchar(128) NOT NULL,
    post_data date NOT NULL,
    content varchar(512) NOT NULL,
    img_url varchar(128),
    PRIMARY KEY (id)
);