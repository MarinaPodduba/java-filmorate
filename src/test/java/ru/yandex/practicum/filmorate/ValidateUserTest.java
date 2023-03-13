package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.inMemory.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateUserTest {
    private final User user = new User();
    UserStorage storage = new InMemoryUserStorage();
    private final UserController userController = new UserController(new UserService(storage));
    private ValidationException exception;

    @BeforeEach
    public void setProperties() {
        user.setId(1);
        user.setLogin("user");
        user.setBirthday(LocalDate.now());
        user.setName("name user");
        user.setEmail("email@mail.ru");
    }

    @Test
    public void checkValidationUserExceptionDate() {
        user.setBirthday(LocalDate.of(2025,1,1));
        exception = assertThrows(ValidationException.class,
                () -> userController.addUser(user));
        assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }

    @Test
    public void checkValidationUserExceptionLogin() {
        user.setLogin("");
        exception = assertThrows(ValidationException.class,
                () -> userController.addUser(user));
        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    public void checkValidationUserExceptionLogin2() {
        user.setLogin("login login");
        exception = assertThrows(ValidationException.class,
                () -> userController.addUser(user));
        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    public void checkEmptyName() {
        user.setName(null);
        User newUser = userController.addUser(user);
        assertEquals(newUser.getName(), newUser.getLogin());
    }

    @Test
    public void checkForCorrectLogin(){
        user.setLogin("UserLogin");
        assertDoesNotThrow(() -> userController.addUser(user));
    }

    @Test
    public void checkForCorrectName(){
        user.setName("UserName");
        assertDoesNotThrow(() -> userController.addUser(user));
    }

    @Test
    public void checkForCorrectBirthday(){
        user.setBirthday(LocalDate.of(1998, 2, 18));
        assertDoesNotThrow(() -> userController.addUser(user));
    }
}
