package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public abstract class AbstractData {
    @EqualsAndHashCode.Exclude
    protected long id;
}
