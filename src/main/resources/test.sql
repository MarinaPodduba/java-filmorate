INSERT INTO users (name, login, email, birthday) VALUES ('Name1', 'yandex1', 'yandex1@yandex.ru', '1990-08-20');
INSERT INTO users (name, login, email, birthday) VALUES ('Name2', 'yandex2', 'yandex2@yandex.ru', '1994-03-14');
INSERT INTO users (name, login, email, birthday) VALUES ('Name3', 'yandex3', 'yandex3@yandex.ru', '2000-01-05');

INSERT INTO friendship (user_id, friend_id) VALUES (1, 3);
INSERT INTO friendship (user_id, friend_id) VALUES (2, 3);

MERGE INTO mpa
    KEY(id)
VALUES  (1, 'G'),
        (2, 'PG'),
        (3, 'PG-13'),
        (4, 'R'),
        (5, 'NC-17');

MERGE INTO genre
    KEY(id)
VALUES  (1, 'Комедия'),
        (2, 'Драма'),
        (3, 'Мультфильм'),
        (4, 'Триллер'),
        (5, 'Документальный'),
        (6, 'Боевик');

INSERT INTO films (name, description, releasedate, duration, mpa_id)
VALUES ('film1', 'description1', '2001-01-01', 100, 1);
INSERT INTO films (name, description, releasedate, duration, mpa_id)
VALUES ('film2', 'description2', '2002-02-02', 200, 3);