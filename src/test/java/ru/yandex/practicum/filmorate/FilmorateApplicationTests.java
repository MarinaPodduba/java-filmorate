package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FilmDao;
import ru.yandex.practicum.filmorate.storage.dao.UserDao;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final FilmDbStorage filmStorage;
    private final UserDbStorage userStorage;
    private final FilmDao filmDao;
    private final UserDao userDao;
    private final List<User> users = new ArrayList<>();
    private final List<Film> films = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        Film filmOne = new Film();
        Mpa mpaOne = new Mpa();
        filmOne.setId(1L);
        filmOne.setName("film1");
        filmOne.setDescription("description1");
        filmOne.setReleaseDate(LocalDate.of(2001, 1, 1));
        filmOne.setDuration(100L);
        mpaOne.setId(1L);
        mpaOne.setName("G");
        filmOne.setMpa(mpaOne);

        Film filmTwo = new Film();
        Mpa mpaTwo = new Mpa();
        filmTwo.setId(2L);
        filmTwo.setName("film2");
        filmTwo.setDescription("description2");
        filmTwo.setReleaseDate(LocalDate.of(2002, 2, 2));
        filmTwo.setDuration(200L);
        mpaTwo.setId(3L);
        mpaTwo.setName("PG-13");
        filmTwo.setMpa(mpaTwo);

        films.add(filmOne);
        films.add(filmTwo);

        User userOne = new User();
        userOne.setId(1L);
        userOne.setName("Name1");
        userOne.setLogin("yandex1");
        userOne.setEmail("yandex1@yandex.ru");
        userOne.setBirthday(LocalDate.of(1990, 8, 20));

        User userTwo = new User();
        userTwo.setId(2L);
        userTwo.setName("Name2");
        userTwo.setLogin("yandex2");
        userTwo.setEmail("yandex2@yandex.ru");
        userTwo.setBirthday(LocalDate.of(1994, 3, 14));

        User friend = new User();
        friend.setId(3L);
        friend.setName("Name3");
        friend.setLogin("yandex3");
        friend.setEmail("yandex3@yandex.ru");
        friend.setBirthday(LocalDate.of(2000, 1, 5));

        users.add(userOne);
        users.add(userTwo);
        users.add(friend);
    }

    @Test
    public void testGetFilmWithIncorrectId() {
        assertThrows(NotFoundException.class, () -> filmStorage.get(3));
    }

    @Test
    public void testGetFilms() {
        assertThat(filmStorage.getAll().size()).isEqualTo(0);
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testGetFilmById() {
        Film dbFilm = filmStorage.get(1L);

        Assertions.assertEquals(films.get(0).getId(), dbFilm.getId());
        Assertions.assertEquals(films.get(0).getName(), dbFilm.getName());
        Assertions.assertEquals(films.get(0).getDescription(), dbFilm.getDescription());
        Assertions.assertEquals(films.get(0).getReleaseDate(), dbFilm.getReleaseDate());
        Assertions.assertEquals(films.get(0).getDuration(), dbFilm.getDuration());
        Assertions.assertEquals(films.get(0).getMpa().getName(), dbFilm.getMpa().getName());
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testDeleteFilmById() {
        filmStorage.delete(films.get(0).getId());
        Assertions.assertThrows(NotFoundException.class, () -> {
            filmStorage.get(films.get(0).getId());
        });
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testUpdateFilmById() {
        Film film = films.get(0);
        film.setName("Film updated");

        filmStorage.update(film);
        Film dbFilm = filmStorage.get(film.getId());
        Assertions.assertEquals(film.getId(), dbFilm.getId());
        Assertions.assertEquals(film.getName(), dbFilm.getName());
        Assertions.assertEquals(film.getDescription(), dbFilm.getDescription());
        Assertions.assertEquals(film.getReleaseDate(), dbFilm.getReleaseDate());
        Assertions.assertEquals(film.getDuration(), dbFilm.getDuration());
        Assertions.assertEquals(film.getMpa().getName(), dbFilm.getMpa().getName());
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testGetAllFilms() {
        List<Film> dbList = filmStorage.getAll();
        Assertions.assertEquals(films.size(), dbList.size());
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testGetUserById() {
        User dbUser = userStorage.get(1L);

        Assertions.assertEquals(users.get(0).getName(), dbUser.getName());
        Assertions.assertEquals(users.get(0).getLogin(), dbUser.getLogin());
        Assertions.assertEquals(users.get(0).getEmail(), dbUser.getEmail());
        Assertions.assertEquals(users.get(0).getBirthday(), dbUser.getBirthday());
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testDeleteUserById() {
        userStorage.delete(1L);
        Assertions.assertThrows(NotFoundException.class, () -> {
            userStorage.get(1L);
        });
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testUpdateUserById() {
        User user = users.get(1);
        user.setEmail("qwerty@yandex.ru");
        userStorage.update(user);

        User dbUser = userStorage.get(2L);
        Assertions.assertEquals(user.getName(), dbUser.getName());
        Assertions.assertEquals(user.getLogin(), dbUser.getLogin());
        Assertions.assertEquals(user.getEmail(), dbUser.getEmail());
        Assertions.assertEquals(user.getBirthday(), dbUser.getBirthday());
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testGetAll() {
        List<User> dbList = userStorage.getAll();
        Assertions.assertEquals(users.size(), dbList.size());
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testAddAndGetFriend() {
        userDao.addFriend(users.get(0).getId(), users.get(2).getId());
        List<User> friends = userDao.getFriends(users.get(0).getId());
        Assertions.assertEquals(users.get(2).getName(), friends.get(0).getName());
        Assertions.assertEquals(users.get(2).getLogin(), friends.get(0).getLogin());
        Assertions.assertEquals(users.get(2).getEmail(), friends.get(0).getEmail());
        Assertions.assertEquals(users.get(2).getBirthday(), friends.get(0).getBirthday());
    }

    @Test
    @Sql({"/schema.sql", "/test.sql"})
    public void testDeleteFriend() {
        userDao.removeFriend(users.get(0).getId(), users.get(2).getId());
        List<User> friends = userDao.getFriends(users.get(0).getId());

        Assertions.assertEquals(0, friends.size());
    }
}