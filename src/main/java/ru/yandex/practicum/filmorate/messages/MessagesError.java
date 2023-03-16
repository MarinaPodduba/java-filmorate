package ru.yandex.practicum.filmorate.messages;

public class MessagesError {
    public static final String HANDLE_VALIDATION_EXCEPTION = "400. Ошибка валидации";
    public static final String HANDLE_NOT_FOUND_EXCEPTION = "404. Объект не найден";
    public static final String HANDLE_EXCEPTION = "500. Возникло исключение";
    public static final String LOG_HANDLE_VALIDATION_EXCEPTION = "Возникла ошибка 400: {}";
    public static final String LOG_NOT_FOUND_EXCEPTION = "Возникла ошибка 404: {}";
    public static final String LOG_HANDLE_EXCEPTION = "Возникла ошибка 500: {}";
    public static final String DATA_VERIFICATION = "Данные по id %d отсутствуют";
    public static final String RELEASE_DATA = "Дата релиза должна быть не раньше ";
}