package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController {
    private final HashMap<Long, Film> films = new HashMap<>();
    private long idFilm = 1;

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        log.info("Получен запрос POST /films");
        film.setId(idFilm++);
        validateFilm(film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updetaFilm(@RequestBody Film film) {
        log.info("Получен запрос PUT /films");
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Указанного фильма не существует");
        }
        validateFilm(film);
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получен запрос GET /films");
        return new ArrayList<>(films.values());
    }

    public void validateFilm(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
