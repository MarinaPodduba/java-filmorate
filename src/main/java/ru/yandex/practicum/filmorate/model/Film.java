package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Film extends AbstractData {
    @NotBlank
    @NotNull
    private String name;
    @Size(max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Min(1)
    private Long duration;
    @JsonIgnore
    private Set<Long> likes = new HashSet<>();

    public void addLike(Long id) {
        likes.add(id);
    }

    public boolean removeLike(Long id) {
        return likes.remove(id);
    }

    public int getSizeListLikes() {
        return likes.size();
    }
}
