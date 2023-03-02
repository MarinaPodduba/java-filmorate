package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateUserTest {
    User user = new User();
    UserController userController = new UserController();
    ValidationException exception;

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
    public void checkValidationUserExceptionLogin2() {
        user.setLogin("login login");
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

    @Test
    public void checkForCorrectEmail(){
        user.setEmail("user@yandex.ru");
        assertDoesNotThrow(() -> userController.validateUser(user));
    }

    @Test
    public void checkForCorrectLogin(){
        user.setLogin("UserLogin");
        assertDoesNotThrow(() -> userController.validateUser(user));
    }

    @Test
    public void checkForCorrectName(){
        user.setName("UserName");
        assertDoesNotThrow(() -> userController.validateUser(user));
    }

    @Test
    public void checkForCorrectBirthday(){
        user.setBirthday(LocalDate.of(1998, 2, 18));
        assertDoesNotThrow(() -> userController.validateUser(user));
    }
}
