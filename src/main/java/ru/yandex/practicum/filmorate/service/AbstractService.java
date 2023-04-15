package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.AbstractData;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public abstract class AbstractService<T extends AbstractData> {
    protected Storage<T> storage;
    private Long id = 0L;

    protected AbstractService(Storage<T> storage) {
        this.storage = storage;
    }

    public T add(T data) {
        data.setId(++id);
        storage.add(data);
        return data;
    }

    public T update(T data) {
        storage.update(data);
        return data;
    }

    public T get(long id) {
        T data = storage.get(id);
        return data;
    }

    public List<T> getAll() {
        List<T> listData = storage.getAll();
        return listData;
    }
}