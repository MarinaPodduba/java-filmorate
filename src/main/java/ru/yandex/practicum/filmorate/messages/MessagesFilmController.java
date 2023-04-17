package ru.yandex.practicum.filmorate.messages;

public class MessagesFilmController {
    public static final String ADD_FILM = "Получен запрос POST /films";
    public static final String UPDATE_FILM = "Получен запрос PUT /films";
    public static final String GET_ALL_FILMS = "Получен запрос GET /films";
    public static final String ADD_LIKE = "Получен запрос PUT /films/{id}/like/{userId}";
    public static final String GET_FILM = "Получен запрос GET /films/{id}";
    public static final String GET_POPULAR_FILMS = "Получен запрос GET /films/popular";
    public static final String DELETE_LIKE = "Получен запрос DELETE /films/{id}/like/{userId}";
    public static final String GEL_ALL_GENRES = "Получен запрос GET /genres";
    public static final String GET_GENRE = "Получен запрос GET /genres/{id}";
    public static final String GET_ALL_MPA = "Получен запрос GET /mpa";
    public static final String GET_MPA = "Получен запрос GET /mpa/{id}";
}