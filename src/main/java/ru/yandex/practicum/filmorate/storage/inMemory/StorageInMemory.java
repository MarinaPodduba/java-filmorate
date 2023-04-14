package ru.yandex.practicum.filmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.AbstractData;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.messages.MessagesError.DATA_VERIFICATION;

@Slf4j
public abstract class StorageInMemory<T extends AbstractData> implements Storage<T> {
    private final Map<Long, T> storage = new HashMap<>();
    private long id = 0L;

    @Override
    public T add(T data) {
        data.setId(id);
        storage.put(id++, data);
        log.info("Успешно добавлены данные {}", data);
        return data;
    }

    @Override
    public T update(T data) {
        dataVerification(data.getId());
        if (storage.containsKey(data.getId())) {
            storage.put(data.getId(), data);
        } else {
            throw new NotFoundException("id не найден");
        }
        log.info("Успешно обновлен объект: {}", data);
        return data;
    }

    @Override
    public void delete(long id) {
        dataVerification(id);
        if (!storage.containsKey(id)) {
            throw new NotFoundException("id не найден");
        }
        storage.remove(id);
        log.info("Успешно удален объект под id {}", id);
    }

    @Override
    public T get(long id) {
        dataVerification(id);
        if (storage.containsKey(id)) {
            log.info("Успешно получен объект под id {}", id);
            return storage.get(id);
        } else {
            throw new NotFoundException("id не найден");
        }
    }

    @Override
    public List<T> getAll() {
        log.info("Успешно получены все объекты");
        return new ArrayList<>(storage.values());
    }

    private void dataVerification(long id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException(String.format(DATA_VERIFICATION, id));
        }
    }
}
