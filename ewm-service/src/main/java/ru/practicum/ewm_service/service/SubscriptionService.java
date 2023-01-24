package ru.practicum.ewm_service.service;

import ru.practicum.ewm_service.entity.dto.subscription.SubscriptionDto;
import ru.practicum.ewm_service.entity.dto.user.UserDto;
import ru.practicum.ewm_service.entity.shorts.EventShort;
import ru.practicum.ewm_service.entity.util.Status;

import java.util.List;

public interface SubscriptionService {

    void addRequestForFriendship(Long userId, Long friendId);

    void canselRequestForFriendship(Long userId, Long friendId);

    void confirmRequestForFriendship(Long userId, Long friendId);

    void rejectRequestForFriendship(Long userId, Long friendId);

    UserDto getFriendById(Long userId, Long friendId);

    List<UserDto> getAllFriends(Long userId);

    List<EventShort> getEventsWithFriendsParticipation(Long userId, Long friendId, int from, int size);

    List<SubscriptionDto> getAllWithParameters(
            List<Long> users, List<Long> friends, List<Status> statuses, int from, int size);


}
