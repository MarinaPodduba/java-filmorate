package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserController {
    private final HashMap<Long, User> users = new HashMap<>();
    private long idUser = 1;

    @PostMapping
    public User addUser(@RequestBody User user) {
        log.info("Получен запрос POST /users");
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        validateUser(user);
        user.setId(idUser++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Получен запрос PUT /users");
        if (!users.containsKey(user.getId()))
            throw new ValidationException("Не удается найти указанного пользователя");
        validateUser(user);
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Получен запрос GET /users");
        return new ArrayList<>(users.values());
    }

    public void validateUser(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isBlank()) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isBlank() || user.getLogin() == null) {
            throw new ValidationException("Обязательно должно быть заполнено имя или логин");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}
