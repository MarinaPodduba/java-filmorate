package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private long id;
    @Email
    private String email;
    @NotNull
    private String login;
    private String name;
    private LocalDate birthday;
}
