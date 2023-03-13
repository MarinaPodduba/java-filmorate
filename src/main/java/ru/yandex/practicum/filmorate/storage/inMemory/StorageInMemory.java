package ru.yandex.practicum.filmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.AbstractData;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class StorageInMemory<T extends AbstractData> implements Storage<T> {
    private final Map<Long, T> storage = new HashMap<>();
    private long id = 0L;

    @Override
    public T add(T data) {
        if (storage.containsValue(data)) {
            log.error("Данные уже сужествуют");
            throw new ValidationException("Данные уже существуют");
        }
        id++;
        data.setId(id);
        storage.put(data.getId(), data);
        log.info("Успешно добавлены данные {}", data);
        return data;
    }

    @Override
    public void update(T data) {
        if (!storage.containsKey(data.getId())) {
            throw new NotFoundException("Данные по id " + data.getId() + " отсутствуют");
        }
        storage.put(data.getId(), data);
    }

    @Override
    public void delete(long id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException("Данные по id " + id + " отсутствуют");
        }
        storage.remove(id);
    }

    @Override
    public T get(long id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException("Данные по id " + id + " отсутствуют");
        }
        return storage.get(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }
}
