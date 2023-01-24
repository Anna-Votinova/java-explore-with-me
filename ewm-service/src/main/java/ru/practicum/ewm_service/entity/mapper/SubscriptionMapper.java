package ru.practicum.ewm_service.entity.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm_service.entity.Subscription;
import ru.practicum.ewm_service.entity.dto.subscription.SubscriptionDto;
import ru.practicum.ewm_service.entity.dto.user.UserDto;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionMapper {

    public static SubscriptionDto toDto(Subscription sub) {
        return SubscriptionDto.builder()
                .id(sub.getId())
                .user(UserDto.builder()
                        .id(sub.getUser().getId())
                        .name(sub.getUser().getName())
                        .email(sub.getUser().getEmail())
                        .build())
                .friend(UserDto.builder()
                        .id(sub.getFriend().getId())
                        .name(sub.getFriend().getName())
                        .email(sub.getFriend().getEmail())
                        .build())
                .status(sub.getStatus())
                .build();
    }
}
