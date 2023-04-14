package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.messages.MessagesFilmController.DET_ALL_MPA;
import static ru.yandex.practicum.filmorate.messages.MessagesFilmController.DET_MPA_BY_ID;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaController {
    private final FilmService filmService;

    public MpaController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Mpa> getAllMpa() {
        log.info(DET_ALL_MPA);
        return filmService.getAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable Integer id) {
        log.info(DET_MPA_BY_ID);
        return filmService.getMpaById(id);
    }
}