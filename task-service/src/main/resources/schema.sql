CREATE TABLE IF NOT EXISTS tasks (
    id BINARY(16) NOT NULL,
    description VARCHAR(256) NOT NULL,
    created_at DATETIME NOT NULL,
    deadline DATE NOT NULL,
    status VARCHAR(24) NOT NULL,
    priority VARCHAR(24) NOT NULL,
    should_be_inspected BOOLEAN NOT NULL,
    executor_id BINARY(16) NOT NULL,
    inspector_id BINARY(16) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS comments (
    id BINARY(16) NOT NULL,
    text VARCHAR(256) NOT NULL,
    created_at DATETIME NOT NULL,
    comment_status VARCHAR(24) NOT NULL,
    task_id BINARY(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id) REFERENCES workflow.tasks(id)
);
CREATE TABLE IF NOT EXISTS task_events (
    id BINARY(16) NOT NULL PRIMARY KEY,
    task_id BINARY(16) NOT NULL,
    old_status VARCHAR(24) NULL,
    new_status VARCHAR(24) NOT NULL,
    changed_at DATETIME NOT NULL,
    changed_by BINARY(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id) REFERENCES workflow.tasks(id)
    );