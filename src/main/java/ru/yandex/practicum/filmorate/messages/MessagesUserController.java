package ru.yandex.practicum.filmorate.messages;

public class MessagesUserController {
    public static final String ADD_USER = "Получен запрос POST /users";
    public static final String UPDATE_USER = "Получен запрос PUT /users";
    public static final String GET_ALL_USERS = "Получен запрос GET /users";
    public static final String ADD_FRIEND = "Получен запрос PUT /users/{id}/friends/{friendId}";
    public static final String GET_ALL_FRIENGS = "Получен запрос GET /users/{id}/friends";
    public static final String GET_USER = "Получен запрос GET /users/{id}";
    public static final String GET_COMMON_FRIENDS = "Получен запрос GET /users/{id}/friends/common/{otherId}";
    public static final String DELETE_FRIEND = "Получен запрос DELETE /users/{id}/friends/{friendId}";
}