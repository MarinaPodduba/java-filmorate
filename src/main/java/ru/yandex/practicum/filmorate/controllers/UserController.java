package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.messages.MessagesUserController.*;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserController {
    private final HashMap<Long, User> users = new HashMap<>();
    private long idUser = 1;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info(ADD_USER);
        validateUser(user);
        user.setId(idUser++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info(UPDATE_USER);
        if (!users.containsKey(user.getId()))
            throw new ValidationException("Не удается найти указанного пользователя");
        validateUser(user);
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info(GET_ALL_USERS);
        return new ArrayList<>(users.values());
    }

    public void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}
