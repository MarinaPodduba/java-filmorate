package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mapper.GenreMapper;

import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.filmorate.messages.MessagesError.DATA_NOT_FOUND;

@Slf4j
@Component
@Qualifier("dbGenreStorage")
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Genre data) {
        String sql = "INSERT INTO genre (name) " +
                "VALUES (?)";
        jdbcTemplate.update(sql, data.getName());
    }

    @Override
    public Genre get(long id) {
        String sql = "SELECT g.id AS genre_id, g.name AS genre_name " +
                "FROM genre AS g " +
                "WHERE g.id = ?";
        List<Genre> mpaList = jdbcTemplate.query(sql, GenreMapper::makeGenre, id);
        if (mpaList.isEmpty()) {
            throw new NotFoundException(DATA_NOT_FOUND);
        }
        return mpaList.get(0);
    }

    @Override
    public void update(Genre data) {
        String sql = "UPDATE genre SET name = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, data.getName(), data.getId());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM genre " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT g.id AS genre_id, g.name AS genre_name " +
                "FROM genre AS g";
        List<Genre> genreList = jdbcTemplate.query(sql, GenreMapper::makeGenre);
        return Collections.unmodifiableList(genreList);
    }
}
