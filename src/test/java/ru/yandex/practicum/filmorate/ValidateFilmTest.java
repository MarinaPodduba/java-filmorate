package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateFilmTest {
    Film film = new Film();
    FilmController filmController = new FilmController();
    ValidationException exception;

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
        exception = assertThrows(ValidationException.class,
                () -> filmController.validateFilm(film));
        assertEquals("Дата релиза должна быть не раньше 28 декабря 1895 года", exception.getMessage());
    }

    @Test
    public void checkValidationFilmExceptionDescription() {
        film.setDescription("Инженер-изобретатель Тимофеев сконструировал машину времени, которая соединила его " +
                "квартиру с далеким шестнадцатым веком - точнее, с палатами государя Ивана Грозного. " +
                "Туда-то и попадают тезка царя пенсионер-общественник Иван Васильевич Бунша и " +
                "квартирный вор Жорж Милославский. На их место в двадцатом веке «переселяется» великий государь. " +
                "Поломка машины приводит ко множеству неожиданных и забавных событий...");
        exception = assertThrows(ValidationException.class,
                () -> filmController.validateFilm(film));
        assertEquals("Максимальная длина описания — 200 символов", exception.getMessage());
    }

    @Test
    public void checkValidationFilmExceptionName() {
        film.setName("");
        exception = assertThrows(ValidationException.class,
                () -> filmController.validateFilm(film));
        assertEquals("Название не может быть пустым", exception.getMessage());
    }

    @Test
    public void checkValidationFilmExceptionDuration(){
        film.setDuration(-20L);
        exception = assertThrows(ValidationException.class,
                () -> filmController.validateFilm(film));
        assertEquals("Продолжительность фильма должна быть положительной", exception.getMessage());
    }

    @Test
    public void checkForCorrectDate(){
        film.setReleaseDate(LocalDate.of(2022,1,20));
        assertDoesNotThrow(() -> filmController.validateFilm(film));
    }

    @Test
    public void checkForCorrectDescription(){
        film.setDescription("Инженер-изобретатель Тимофеев сконструировал машину времени, которая соединила " +
                "его квартиру с далеким шестнадцатым веком - точнее, с палатами государя Ивана Грозного.");
        assertDoesNotThrow(() -> filmController.validateFilm(film));
    }

    @Test
    public void checkForCorrectDuration(){
        film.setDuration(35L);
        assertDoesNotThrow(() -> filmController.validateFilm(film));
    }

    @Test
    public void checkForCorrectName(){
        film.setName("Иван Васильевич меняет профессию");
        assertDoesNotThrow(() -> filmController.validateFilm(film));
    }
}
