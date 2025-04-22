-- liquibase formatted sql
-- changeset rubtsov:1
CREATE TABLE notification_task (
    id SERIAL PRIMARY KEY,
    chat_id INT,
    date_sending TIMESTAMP,
    msg TEXT
);

-- changeset rubtsov:2
CREATE INDEX notification_task__chat_id ON notification_task(chat_id);
CREATE INDEX notification_task__date_sending ON notification_task(date_sending);

-- changeset rubtsov:3
ALTER TABLE notification_task ADD sended BOOLEAN NOT NULL DEFAULT FALSE;
CREATE INDEX notification_task__sended ON notification_task(sended);
