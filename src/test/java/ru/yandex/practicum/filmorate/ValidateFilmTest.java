package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.inMemory.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.inMemory.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.inMemory.StorageInMemory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.service.FilmService.MIN_DATA;

public class ValidateFilmTest {
    private final Film film = new Film();
    private final StorageInMemory<Film> filmStorage = new InMemoryFilmStorage();
    private final UserService userService = new UserService(new InMemoryUserStorage());
    private final FilmController filmController = new FilmController(new FilmService(filmStorage, userService));

    @BeforeEach
    public void setProperties() {
        film.setId(1L);
        film.setDuration(10L);
        film.setName("name film");
        film.setDescription("new film");
        film.setReleaseDate(LocalDate.now());
    }

    @Test
    public void checkValidationFilmExceptionDate() {
        film.setReleaseDate(LocalDate.of(999,12,5));
        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmController.addFilm(film));
        assertEquals("Дата релиза должна быть не раньше " + MIN_DATA, exception.getMessage());
    }

    @Test
    public void checkForCorrectDate(){
        film.setReleaseDate(LocalDate.of(2022,1,20));
        assertDoesNotThrow(() -> filmController.addFilm(film));
    }
}
