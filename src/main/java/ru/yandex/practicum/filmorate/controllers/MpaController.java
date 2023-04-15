package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

import static ru.yandex.practicum.filmorate.messages.MessagesFilmController.GET_ALL_MPA;
import static ru.yandex.practicum.filmorate.messages.MessagesFilmController.GET_MPA;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<Mpa> getAll() {
        log.info(GET_ALL_MPA);
        return mpaService.getAll();
    }

    @GetMapping("{id}")
    public Mpa get(@PathVariable long id) {
        log.info(GET_MPA);
        return mpaService.get(id);
    }
}
