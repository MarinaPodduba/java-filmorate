package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.AbstractData;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.storage.mapper.GenreMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.messages.Constants.FORMATTER;
import static ru.yandex.practicum.filmorate.messages.MessagesError.DATA_NOT_FOUND;

@Slf4j
@Component
@Qualifier("dbFilmStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Film data) {
        String sql = "INSERT INTO films (name, description, releasedate, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                data.getName(),
                data.getDescription(),
                data.getReleaseDate().format(FORMATTER),
                data.getDuration(),
                data.getMpa().getId()
        );

        String sqlGenre = "INSERT INTO film_genre (film_id, genre_id) " +
                "VALUES (?, ?)";
        batchUpdate(
                sqlGenre,
                data.getId(),
                data.getGenres().stream().map(AbstractData::getId).collect(Collectors.toList())
        );
    }

    @Override
    public Film get(long id) {
        String sql = "SELECT f.id, f.name, f.description, f.releasedate, f.duration, f.mpa_id, m.name AS mpa_name " +
                "FROM films f, mpa m " +
                "WHERE f.mpa_id = m.id AND f.id = ?";
        List<Film> filmList = jdbcTemplate.query(sql, FilmMapper::makeFilm, id);
        if (filmList.isEmpty()) {
            throw new NotFoundException(DATA_NOT_FOUND);
        }
        Film film = filmList.get(0);
        String sqlGet = "SELECT fg.film_id AS film_id, g.id AS genre_id, g.name AS genre_name " +
                "FROM film_genre AS fg, genre AS g " +
                "WHERE fg.genre_id = g.id " +
                "AND fg.film_id IN (%s)";
        List<Genre> filmGenres = jdbcTemplate.query(String.format(sqlGet, "?"), GenreMapper::makeGenre, id);
        film.getGenres().addAll(filmGenres);
        return film;
    }

    @Override
    public void update(Film data) {
        Film film = get(data.getId());
        String sql = "UPDATE films SET name = ?, description = ?, releasedate = ?, duration = ?, mpa_id = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(
                sql,
                data.getName(),
                data.getDescription(),
                data.getReleaseDate().format(FORMATTER),
                data.getDuration(),
                data.getMpa().getId(),
                data.getId()
        );

        List<Long> filmGenreIds = film.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList());
        List<Long> dataGenreIds = data.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList());
        List<Long> genreIdsForInsert =
                dataGenreIds
                        .stream()
                        .filter(genreId -> !filmGenreIds.contains(genreId))
                        .collect(Collectors.toList());

        String sqlGenre = "INSERT INTO film_genre (film_id, genre_id) " +
                "VALUES (?, ?)";
        batchUpdate(sqlGenre, data.getId(), genreIdsForInsert);

        List<Long> genreIdsForDelete =
                filmGenreIds
                        .stream()
                        .filter(genreId -> !dataGenreIds.contains(genreId))
                        .collect(Collectors.toList());
        String sqlDelete = "DELETE FROM film_genre " +
                "WHERE film_id = ? " +
                "AND genre_id = ?";

        batchUpdate(sqlDelete, data.getId(), genreIdsForDelete);
        String sqlGet = "SELECT fg.film_id AS film_id, g.id AS genre_id, g.name AS genre_name " +
                "FROM film_genre AS fg, genre AS g " +
                "WHERE fg.genre_id = g.id " +
                "AND fg.film_id IN (%s)";

        List<Genre> filmGenres =
                jdbcTemplate.query(String.format(sqlGet, "?"), GenreMapper::makeGenre, data.getId());
        data.setGenres(filmGenres.stream().collect(Collectors.toSet()));
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM films " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT f.id, f.name, f.description, f.releasedate, f.duration, f.mpa_id, m.name AS mpa_name " +
                "FROM films f, mpa m " +
                "WHERE f.mpa_id = m.id";
        List<Film> films = jdbcTemplate.query(sql, FilmMapper::makeFilm);
        Map<Long, Film> filmsMap = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));

        String filmIdsStr = String.join(",", Collections.nCopies(films.size(), "?"));
        String sqlGet = "SELECT fg.film_id AS film_id, g.id AS genre_id, g.name AS genre_name " +
                "FROM film_genre AS fg, genre AS g " +
                "WHERE fg.genre_id = g.id " +
                "AND fg.film_id IN (%s)";
        jdbcTemplate.query(
                String.format(sqlGet, filmIdsStr),
                (rs, rowNum) ->
                        filmsMap.get(
                                rs.getLong("film_id")
                        ).getGenres().add(GenreMapper.makeGenre(rs, rowNum)),
                films.stream().map(Film::getId).collect(Collectors.toList()).toArray()
        );
        return Collections.unmodifiableList(films);
    }

    private int[] batchUpdate(String updateQuery, long filmId, List<Long> genreIds) {
        int[] updateCounts = jdbcTemplate.batchUpdate(
                updateQuery,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, filmId);
                        ps.setLong(2, genreIds.get(i));
                    }

                    public int getBatchSize() {
                        return genreIds.size();
                    }
                });
        return updateCounts;
    }
}
