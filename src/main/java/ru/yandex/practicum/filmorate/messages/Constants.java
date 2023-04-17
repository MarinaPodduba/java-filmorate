package ru.yandex.practicum.filmorate.messages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Constants {
    public static final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
