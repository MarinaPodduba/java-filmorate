package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.mapper.UserMapper;

import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.filmorate.messages.Constants.FORMATTER;
import static ru.yandex.practicum.filmorate.messages.MessagesError.DATA_NOT_FOUND;

@Slf4j
@Component
@Qualifier("dbUserStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(User data) {
        String sql = "INSERT INTO users (name, login, email, birthday) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                data.getName(),
                data.getLogin(),
                data.getEmail(),
                data.getBirthday().format(FORMATTER)
        );
    }

    @Override
    public User get(long id) {
        String sql = "SELECT id, name, login, email, birthday " +
                "FROM users " +
                "WHERE id = ?";
        List<User> userList = jdbcTemplate.query(sql, UserMapper::makeUser, id);
        if (userList.isEmpty()) {
            throw new NotFoundException(DATA_NOT_FOUND);
        }
        return userList.get(0);
    }

    @Override
    public void update(User data) {
        String sql = "UPDATE users SET name = ?, login = ?, email = ?, birthday = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(
                sql,
                data.getName(),
                data.getLogin(),
                data.getEmail(),
                data.getBirthday().format(FORMATTER),
                data.getId()
        );
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE " +
                "FROM users " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT id, name, login, email, birthday " +
                "FROM users";
        List<User> users = jdbcTemplate.query(sql, UserMapper::makeUser);
        return Collections.unmodifiableList(users);
    }
}
