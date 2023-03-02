package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Film {
    private Long id;
    @NotNull
    private String name;
    @Size(max=200)
    private String description;
    private LocalDate releaseDate;
    @Min(1)
    private Long duration;
}
