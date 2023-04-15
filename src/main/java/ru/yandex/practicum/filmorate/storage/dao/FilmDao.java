package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao {
    void addLike(long id, long userId);

    void removeLike(long id, long userId);

    List<Film> getPopular(int count);
}
