package ru.practicum.ewm_service.controllers.private_api;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.entity.dto.user.UserDto;
import ru.practicum.ewm_service.entity.shorts.EventShort;
import ru.practicum.ewm_service.service.SubscriptionService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/friends")
@Validated
public class PrivateApiFriendsController {

    private final SubscriptionService subscriptionService;


    @PostMapping("/{friendId}")
    public void addRequestForFriendship(@Positive @PathVariable @NotNull Long userId,
                                        @Positive @PathVariable @NotNull Long friendId) {
        subscriptionService.addRequestForFriendship(userId, friendId);
    }

    @PatchMapping("/{friendId}/cancel")
    public void canselRequestForFriendship(@Positive @PathVariable @NotNull Long userId,
                                           @Positive @PathVariable @NotNull Long friendId) {

        subscriptionService.canselRequestForFriendship(userId, friendId);

    }

    @PatchMapping("/{friendId}/confirm")
    public void confirmRequestForFriendship(@Positive @PathVariable @NotNull Long userId,
                                            @Positive @PathVariable @NotNull Long friendId) {
        subscriptionService.confirmRequestForFriendship(userId, friendId);

    }

    @PatchMapping("/{friendId}/reject")
    public void rejectRequestForFriendship(@Positive @PathVariable @NotNull Long userId,
                                            @Positive @PathVariable @NotNull Long friendId) {
        subscriptionService.rejectRequestForFriendship(userId, friendId);

    }

    @GetMapping("/{friendId}")
    public UserDto getFriendById(@Positive @PathVariable @NotNull Long userId,
                                 @Positive @PathVariable @NotNull Long friendId) {
        return subscriptionService.getFriendById(userId, friendId);

    }

    @GetMapping
    public List<UserDto> getAllFriends(@Positive @PathVariable @NotNull Long userId) {
        return subscriptionService.getAllFriends(userId);
    }


    @GetMapping("/{friendId}/events")
    public List<EventShort> getEventsWithFriendsParticipation(@Positive @PathVariable @NotNull Long userId,
                                                              @Positive @PathVariable @NotNull Long friendId,
                                                              @RequestParam(required = false, defaultValue = "0")
                                                                  @PositiveOrZero int from,
                                                              @RequestParam(required = false, defaultValue = "10")
                                                                  @Positive int size) {
        return subscriptionService.getEventsWithFriendsParticipation(userId, friendId, from, size);
    }



}
