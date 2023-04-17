package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.dao.FilmDao;

import java.util.List;

@Service
public class FilmService extends AbstractService<Film> {
    private final Storage<User> userStorage;
    private final FilmDao filmDao;

    public FilmService(
            @Qualifier("dbFilmStorage") Storage<Film> filmStorage,
            @Qualifier("dbUserStorage") Storage<User> userStorage,
            FilmDao filmDao
    ) {
        super(filmStorage);
        this.userStorage = userStorage;
        this.filmDao = filmDao;
    }

    public void addLike(long id, long userId) {
        Film film = super.get(id);
        User user = userStorage.get(userId);
        filmDao.addLike(film.getId(), user.getId());
    }

    public void deleteLike(long id, long userId) {
        Film film = super.get(id);
        User user = userStorage.get(userId);
        filmDao.removeLike(film.getId(), user.getId());
    }

    public List<Film> getPopularFilms(int count) {
        return filmDao.getPopular(count);
    }
}
