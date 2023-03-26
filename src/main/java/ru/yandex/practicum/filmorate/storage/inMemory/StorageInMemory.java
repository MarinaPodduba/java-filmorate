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

import static ru.yandex.practicum.filmorate.messages.MessagesError.DATA_VERIFICATION;

@Slf4j
public abstract class StorageInMemory<T extends AbstractData> implements Storage<T> {
    private final Map<Long, T> storage = new HashMap<>();
    private long id = 0L;

    @Override
    public T add(T data) {
        if (storage.containsValue(data)) {
            log.error("Данные уже существуют");
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
        dataVerification(data.getId());
        storage.put(data.getId(), data);
        log.info("Успешно обновлен объект: {}", data);
    }

    @Override
    public void delete(long id) {
        dataVerification(id);
        storage.remove(id);
        log.info("Успешно удален объект под id {}", id);
    }

    @Override
    public T get(long id) {
        dataVerification(id);
        log.info("Успешно получен объект под id {}", id);
        return storage.get(id);
    }

    @Override
    public List<T> getAll() {
        log.info("Успешно получены все объекты");
        return new ArrayList<>(storage.values());
    }

    private void dataVerification(long id){
        if (!storage.containsKey(id)) {
            throw new NotFoundException(String.format(DATA_VERIFICATION, id));
        }
    }
}
