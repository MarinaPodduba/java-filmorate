package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Genre extends AbstractData {
    private String name;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
