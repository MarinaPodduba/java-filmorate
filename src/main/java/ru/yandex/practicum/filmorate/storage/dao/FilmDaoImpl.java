package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.mapper.FilmMapper;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(long id, long userId) {
        String sql = "INSERT INTO film_like (film_id, user_id) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public void removeLike(long id, long userId) {
        String sql = "DELETE FROM film_like " +
                "WHERE film_id = ? " +
                "AND user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public List<Film> getPopular(int count) {
        String sql = "SELECT f.id, f.name, f.description, f.releasedate, f.duration, f.mpa_id, m.name " +
                "AS mpa_name, COUNT(f.id) " +
                "FROM films f " +
                "JOIN mpa m ON f.mpa_id = m.id " +
                "LEFT JOIN film_like AS fl ON f.id = fl.film_id " +
                "GROUP BY f.name " +
                "ORDER BY COUNT(f.id) DESC, f.id DESC " +
                "LIMIT ?";
        List<Film> films = jdbcTemplate.query(sql, FilmMapper::makeFilm, count);
        return Collections.unmodifiableList(films);
    }
}
