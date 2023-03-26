package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService<User> {
    public UserService(Storage<User> storage) {
        this.storage = storage;
    }

    @Override
    protected void validator(User data) {
        if (data.getName() == null || data.getName().isBlank()) {
            data.setName(data.getLogin());
        }
        if (data.getLogin().isBlank() || data.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (data.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }

    public void addFriend(long id, long friendId) {
        User user = storage.get(id);
        User friend = storage.get(friendId);
        user.addFriend(friendId);
        friend.addFriend(id);
    }

    public void deleteFriend(long id, long friendId) {
        User user = storage.get(id);
        User friend = storage.get(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(id);
    }

    public List<User> getFriends(long id) {
        User user = storage.get(id);
        return user.getFriends().stream()
                .map(storage::get)
                .collect(Collectors.toList());
    }

    public List<User> getListMutualFriends(long id, long otherId) {
        User user = storage.get(id);
        User other = storage.get(otherId);
        return user.getFriends().stream()
                .filter(other.getFriends()::contains)
                .map(storage::get)
                .collect(Collectors.toList());
    }
}
