package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.AbstractData;

import java.util.List;

public interface Storage<T extends AbstractData> {
    void add(T data);

    void update(T data);

    void delete(long id);

    T get(long id);

    List<T> getAll();
}