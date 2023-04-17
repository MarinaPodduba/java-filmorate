package ru.yandex.practicum.filmorate.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ReleaseValidator.class)
public @interface Release {
    String message() default "{ru.yandex.practicum.filmorate.validation.constraints.Release.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
