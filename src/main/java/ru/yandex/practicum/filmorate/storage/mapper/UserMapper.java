package ru.yandex.practicum.filmorate.storage.mapper;

import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.messages.Constants.FORMATTER;

public class UserMapper {
    public static User makeUser(ResultSet rs, long rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setLogin(rs.getString("login"));
        user.setEmail(rs.getString("email"));
        user.setBirthday(LocalDate.parse(rs.getString("birthday"), FORMATTER));
        return user;
    }
}
