package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.messages.MessagesFilmController.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info(ADD_FILM);
        filmService.add(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info(UPDATE_FILM);
        filmService.update(film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info(GET_ALL_FILMS);
        return filmService.getAll();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        log.info(ADD_LIKE);
        filmService.addLike(id, userId);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        log.info(GET_FILM);
        return filmService.get(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        log.info(DELETE_LIKE);
        filmService.deleteLike(id, userId);
    }

    @GetMapping({"/popular"})
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info(GET_POPULAR_FILMS);
        return filmService.getPopularFilms(count);
    }
}