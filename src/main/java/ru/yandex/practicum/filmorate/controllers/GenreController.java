package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

import static ru.yandex.practicum.filmorate.messages.MessagesFilmController.GEL_ALL_GENRES;
import static ru.yandex.practicum.filmorate.messages.MessagesFilmController.GET_GENRE;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<Genre> getAll() {
        log.info(GEL_ALL_GENRES);
        return genreService.getAll();
    }

    @GetMapping("{id}")
    public Genre get(@PathVariable long id) {
        log.info(GET_GENRE);
        return genreService.get(id);
    }
}