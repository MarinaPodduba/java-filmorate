package ru.yandex.practicum.filmorate.storage.inMemory;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@Component
public class InMemoryFilmStorage extends StorageInMemory<Film> implements FilmStorage {
}
