package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.dao.UserDao;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService extends AbstractService<User> {
    private final UserDao userDao;

    public UserService(@Qualifier("dbUserStorage") Storage<User> userStorage, UserDao userDao) {
        super(userStorage);
        this.userDao = userDao;
    }

    @Override
    public User add(User data) {
        if (data.getName() == null || data.getName().isBlank()) {
            data.setName(data.getLogin());
        }
        if (data.getLogin().isBlank() || data.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (data.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        super.add(data);
        return data;
    }

    public void addFriend(long id, long friendId) {
        User user = super.get(id);
        User friend = super.get(friendId);
        userDao.addFriend(user.getId(), friend.getId());
    }

    public void deleteFriend(long id, long friendId) {
        User user = super.get(id);
        User friend = super.get(friendId);
        userDao.removeFriend(user.getId(), friend.getId());
    }

    public List<User> getFriends(long id) {
        User user = super.get(id);
        return userDao.getFriends(id);
    }

    public List<User> getListMutualFriends(long id, long otherId) {
        User user = super.get(id);
        User otherUser = super.get(otherId);
        return userDao.getCommonFriends(user.getId(), otherUser.getId());
    }
}
