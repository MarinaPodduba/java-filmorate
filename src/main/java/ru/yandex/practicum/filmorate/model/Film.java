package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorate.constraints.Release;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film extends AbstractData {
    @NotBlank
    @NotNull
    private String name;
    @Size(max = 200)
    private String description;
    @NotNull
    @Release
    private LocalDate releaseDate;
    @Min(1)
    @PositiveOrZero
    private Long duration;
    @NotNull
    private Mpa mpa;
    private Set<Genre> genres = new HashSet<>();
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Long> likes = new HashSet<>();
}
