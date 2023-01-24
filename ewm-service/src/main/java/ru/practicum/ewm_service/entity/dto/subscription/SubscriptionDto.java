package ru.practicum.ewm_service.entity.dto.subscription;


import lombok.*;
import ru.practicum.ewm_service.entity.dto.user.UserDto;
import ru.practicum.ewm_service.entity.util.Status;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {

    private Long id;

    private UserDto user;

    private UserDto friend;

    private Status status;

}
