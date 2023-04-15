package ru.yandex.practicum.filmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage extends StorageInMemory<User> implements UserStorage {
}
