package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateTest {
    Film film = new Film();
    FilmController filmController = new FilmController();
    User user = new User();
    UserController userController = new UserController();
    ValidationException exception;

    @BeforeEach
    public void setProperties() {
        film.setId(1L);
        film.setDuration(10L);
        film.setName("name film");
        film.setDescription("new film");
        film.setReleaseDate(LocalDate.now());

        user.setId(1);
        user.setLogin("user");
        user.setBirthday(LocalDate.now());
        user.setName("name user");
        user.setEmail("email@mail.ru");
    }

    @Test
    public void checkValidationFilmExceptionDuration() {
        film.setDuration(-1L);
        exception = assertThrows(ValidationException.class,
                () -> filmController.validateFilm(film));
        assertEquals("Продолжительность фильма должна быть положительной", exception.getMessage());
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
    public void checkValidationUserExceptionDate() {
        user.setBirthday(LocalDate.of(2025,1,1));
        exception = assertThrows(ValidationException.class,
                () -> userController.validateUser(user));
        assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }

    @Test
    public void checkValidationUserExceptionLogin() {
        user.setLogin("");
        exception = assertThrows(ValidationException.class,
                () -> userController.validateUser(user));
        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    public void checkValidationUserExceptionEmail() {
        user.setEmail("");
        exception = assertThrows(ValidationException.class,
                () -> userController.validateUser(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                exception.getMessage());
    }

    @Test
    public void checkEmptyName() {
        user.setName(null);
        User newUser = userController.addUser(user);
        assertEquals(newUser.getName(), newUser.getLogin());
    }
}
