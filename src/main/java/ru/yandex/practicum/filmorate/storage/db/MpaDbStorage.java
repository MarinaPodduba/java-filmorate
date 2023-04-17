package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.mapper.MpaMapper;

import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.filmorate.messages.MessagesError.DATA_NOT_FOUND;

@Slf4j
@Component
@Qualifier("dbMpaStorage")
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Mpa data) {
        String sql = "INSERT INTO mpa (name) " +
                "VALUES (?)";
        jdbcTemplate.update(sql, data.getName());
    }

    @Override
    public Mpa get(long id) {
        String sql = "SELECT m.id AS mpa_id, m.name AS mpa_name " +
                "FROM mpa AS m " +
                "WHERE m.id = ?";
        List<Mpa> mpaList = jdbcTemplate.query(sql, MpaMapper::makeMpa, id);
        if (mpaList.isEmpty()) {
            throw new NotFoundException(DATA_NOT_FOUND);
        }
        return mpaList.get(0);
    }

    @Override
    public void update(Mpa data) {
        String sql = "UPDATE mpa SET name = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, data.getName(), data.getId());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM mpa " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Mpa> getAll() {
        String sql = "SELECT m.id AS mpa_id, m.name AS mpa_name " +
                "FROM mpa AS m";
        List<Mpa> mpaList = jdbcTemplate.query(sql, MpaMapper::makeMpa);
        return Collections.unmodifiableList(mpaList);
    }
}
