CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS app_user_email_uq ON app_user (email);
