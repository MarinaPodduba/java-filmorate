package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.UserMapper;

import java.util.Collections;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(long id, long friendId) {
        String sql = "INSERT INTO friendship (user_id, friend_id) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public void removeFriend(long id, long friendId) {
        String sql = "DELETE " +
                "FROM friendship WHERE user_id = ? " +
                "AND friend_id = ?";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public List<User> getFriends(long id) {
        String sql = "SELECT id, name, login, email, birthday " +
                "FROM users WHERE users.id IN " +
                "(SELECT friend_id FROM friendship WHERE user_id = ?)";
        List<User> friends = jdbcTemplate.query(sql, UserMapper::makeUser, id);
        return Collections.unmodifiableList(friends);
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        String sql = "SELECT u.id, u.name, u.login, u.email, u.birthday " +
                "FROM users u " +
                "WHERE u.id IN (" +
                "SELECT f.friend_id " +
                "FROM friendship f, friendship f2 " +
                "WHERE f.user_id = ? AND f2.user_id = ? AND f.friend_id = f2.friend_id" +
                ")";
        List<User> commonFriends =
                jdbcTemplate.query(sql, UserMapper::makeUser, id, otherId);
        return Collections.unmodifiableList(commonFriends);
    }
}
