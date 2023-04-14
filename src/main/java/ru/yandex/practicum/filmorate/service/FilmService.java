package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.messages.MessagesError.RELEASE_DATA;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    @Getter
    private final UserStorage userStorage;
    private final GenreDbStorage genreStorage;
    private final MpaDbStorage mpaStorage;
    public static final LocalDate MIN_DATA = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       GenreDbStorage genreStorage,
                       MpaDbStorage mpaStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreStorage = genreStorage;
        this.mpaStorage = mpaStorage;
    }

    public Film add(Film film) {
        validator(film);
        filmStorage.add(film);
        return film;
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film get(Long id) {
        return filmStorage.get(id);
    }

    public void addLike(Long filmId, Long userId) {
        userStorage.get(userId);
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        filmStorage.deleteLike(filmId, userId);
    }

    public List<Film> getPopularFilms(Integer filmQuantity) {
        Comparator<Film> filmLikeComparator = (film1, film2) -> {
            if (film1.getLikes().size() == film2.getLikes().size()) {
                return (int) (film1.getId() - film2.getId());
            } else {
                return film1.getLikes().size() - film2.getLikes().size();
            }
        };
        Set<Film> popularFilms = new TreeSet<>(filmLikeComparator.reversed());
        List<Film> films = filmStorage.getAll();
        popularFilms.addAll(films);
        if (Objects.isNull(filmQuantity)) {
            filmQuantity = 10;
        }
        return popularFilms.stream().limit(filmQuantity).collect(Collectors.toList());
    }

    public Collection<Genre> getGenres() {
        return genreStorage.getGenres().stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList());
    }

    public Genre getGenreById(Integer id) {
        return genreStorage.getGenreById(id);
    }

    public Collection<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa().stream()
                .sorted(Comparator.comparing(Mpa::getId))
                .collect(Collectors.toList());
    }

    public Mpa getMpaById(Integer id) {
        return mpaStorage.getMpaById(id);
    }

    protected void validator(Film film) {
        if (film.getReleaseDate().isBefore(MIN_DATA)) {
            throw new ValidationException(RELEASE_DATA + MIN_DATA);
        }
    }
}
