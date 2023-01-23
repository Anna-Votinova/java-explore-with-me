package ru.practicum.ewm_service.entity.mapper;

import ru.practicum.ewm_service.entity.User;
import ru.practicum.ewm_service.entity.dto.user.NewUserRequest;
import ru.practicum.ewm_service.entity.dto.user.UserDto;

public class UserMapper {

    public static User fromDto(NewUserRequest dto) {
        return new User(dto.getName(), dto.getEmail());
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
