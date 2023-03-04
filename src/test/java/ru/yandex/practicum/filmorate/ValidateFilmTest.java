package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateFilmTest {
    private final Film film = new Film();
    private final FilmController filmController = new FilmController();

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
                () -> filmController.validateFilm(film));
        assertEquals("Дата релиза должна быть не раньше 28 декабря 1895 года", exception.getMessage());
    }

    @Test
    public void checkForCorrectDate(){
        film.setReleaseDate(LocalDate.of(2022,1,20));
        assertDoesNotThrow(() -> filmController.validateFilm(film));
    }
}
