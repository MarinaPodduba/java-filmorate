package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.AbstractData;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public abstract class AbstractService<T extends AbstractData> {
    protected Storage<T> storage;

    protected abstract void validator(T data);

    public T add(T data) {
        validator(data);
        return storage.add(data);
    }

    public void update(T data) {
        validator(data);
        storage.update(data);
    }

    public T findById(long id) {
        return storage.get(id);
    }

    public List<T> getAll() {
        return storage.getAll();
    }
}