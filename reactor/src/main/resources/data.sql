INSERT INTO app_user (name, email) VALUES
('Ivan', 'ivan1@mail.ru'),
('Evgeny', 'evgeny.v@yandex.ru'),
('Lisa', 'lisa1998@gmail.com'),
('John', 'john.doe@gmail.com'),
('Kate', 'katakate@gmail.com'),
('Broken', 'broken-no-at-sign')
ON CONFLICT (email) DO NOTHING;
