package ru.practicum.ewm_service.service;

import ru.practicum.ewm_service.entity.dto.user.NewUserRequest;
import ru.practicum.ewm_service.entity.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto createNewUser(NewUserRequest dto);

    List<UserDto> getUsers(List<Long> ids, int from, int size);

    void deleteUser(Long id);
}
