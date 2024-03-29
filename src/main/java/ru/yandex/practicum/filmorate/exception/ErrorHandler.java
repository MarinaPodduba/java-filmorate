package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static ru.yandex.practicum.filmorate.messages.MessagesError.*;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException ex) {
        log.info(LOG_HANDLE_VALIDATION_EXCEPTION, ex.getMessage());
        return Map.of(HANDLE_VALIDATION_EXCEPTION, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final NotFoundException ex) {
        log.info(LOG_NOT_FOUND_EXCEPTION, ex.getMessage());
        return Map.of(HANDLE_NOT_FOUND_EXCEPTION, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleException(final Throwable ex) {
        log.info(LOG_HANDLE_EXCEPTION, ex.getMessage());
        return Map.of(HANDLE_EXCEPTION, ex.getMessage());
    }
}