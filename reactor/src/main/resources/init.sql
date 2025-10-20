CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO app_user (name, email) VALUES
('Ivan', 'ivan1@mail.ru'),
('Evgeny', 'evgeny.v@yandex.ru'),
('Lisa', 'lisa1998@gmail.com'),
('John', 'john.doe@gmail.com'),
('Kate', 'katakate@gmail.com');