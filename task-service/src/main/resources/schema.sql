CREATE TABLE IF NOT EXISTS tasks (
    id binary(16) NOT NULL,
    description varchar(256) NOT NULL,
    creation date NOT NULL,
    deadline date NOT NULL,
    status varchar(24) NOT NULL,
    priority varchar(24) NOT NULL,
    destination varchar(64),
    should_be_inspected boolean NOT NULL,
    executor_id binary(16) NOT NULL,
    inspector_id binary(16) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comments (
    id binary(16) NOT NULL,
    text varchar(256) NOT NULL,
    creation date NOT NULL,
    comment_status varchar(24) NOT NULL,
    task_id binary(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id) REFERENCES workflow.tasks(id)
);