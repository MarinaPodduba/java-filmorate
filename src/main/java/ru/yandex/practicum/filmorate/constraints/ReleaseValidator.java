package ru.yandex.practicum.filmorate.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.messages.Constants.MIN_DATE;

public class ReleaseValidator implements ConstraintValidator<Release, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        }
        return localDate.isAfter(MIN_DATE);
    }
}
