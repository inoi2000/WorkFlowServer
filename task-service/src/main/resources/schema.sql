CREATE TABLE IF NOT EXISTS tasks (
    id BINARY(16) NOT NULL,
    description VARCHAR(256) NOT NULL,
    creation DATE NOT NULL,
    deadline DATE NOT NULL,
    status VARCHAR(24) NOT NULL,
    priority VARCHAR(24) NOT NULL,
    destination VARCHAR(64),
    should_be_inspected BOOLEAN NOT NULL,
    executor_id BINARY(16) NOT NULL,
    inspector_id BINARY(16) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comments (
    id BINARY(16) NOT NULL,
    text VARCHAR(256) NOT NULL,
    creation DATE NOT NULL,
    comment_status VARCHAR(24) NOT NULL,
    task_id BINARY(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id) REFERENCES workflow.tasks(id)
);