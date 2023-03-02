package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.messages.MessagesFilmController.*;

@Validated
@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController {
    private final HashMap<Long, Film> films = new HashMap<>();
    private long idFilm = 1;
    private static final int MAX_SYMBOLS = 200;
    private static final LocalDate MIN_DATA = LocalDate.of(1895, 12, 28);

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info(ADD_FILM);
        film.setId(idFilm++);
        validateFilm(film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updetaFilm(@Valid @RequestBody Film film) {
        log.info(UPDATE_FILM);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Указанного фильма не существует");
        }
        validateFilm(film);
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info(GET_ALL_FILMS);
        return new ArrayList<>(films.values());
    }

    public void validateFilm(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > MAX_SYMBOLS) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(MIN_DATA)) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
