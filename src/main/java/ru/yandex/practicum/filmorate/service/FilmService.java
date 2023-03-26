package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.messages.MessagesError.RELEASE_DATA;

@Service
public class FilmService extends AbstractService<Film> {
    private final UserService userService;
    private static final Comparator<Film> COMPARATOR_FILM = Comparator.comparing(Film::getSizeListLikes).reversed();
    public static final LocalDate MIN_DATA = LocalDate.of(1895, 12, 28);

    public FilmService(Storage<Film> storage, UserService userService) {
        this.userService = userService;
        this.storage = storage;
    }

    @Override
    protected void validator(Film film) {
        if (film.getReleaseDate().isBefore(MIN_DATA)) {
            throw new ValidationException(RELEASE_DATA + MIN_DATA);
        }
    }

    public void addLike(long id, long userId) {
        Film film = storage.get(id);
        userService.storage.get(userId);
        film.addLike(userId);
    }

    public void deleteLike(long id, long userId) {
        Film film = storage.get(id);
        userService.storage.get(userId);
        if (!film.removeLike(userId)) {
            throw new NotFoundException(String.format("Данных по указанному id %d отсутствуют", userId));
        }
    }

    public List<Film> getPopularFilms(Integer count) {
        return storage.getAll()
                .stream().sorted(COMPARATOR_FILM)
                .limit(count)
                .collect(Collectors.toList());
    }
}
