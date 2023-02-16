package ru.practicum.ewm_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_service.entity.Request;
import ru.practicum.ewm_service.entity.Subscription;
import ru.practicum.ewm_service.entity.User;
import ru.practicum.ewm_service.entity.dto.subscription.SubscriptionDto;
import ru.practicum.ewm_service.entity.dto.user.UserDto;
import ru.practicum.ewm_service.entity.mapper.EventMapper;
import ru.practicum.ewm_service.entity.mapper.SubscriptionMapper;
import ru.practicum.ewm_service.entity.mapper.UserMapper;
import ru.practicum.ewm_service.entity.shorts.EventShort;
import ru.practicum.ewm_service.entity.util.Status;
import ru.practicum.ewm_service.repository.RequestsRepository;
import ru.practicum.ewm_service.repository.SubscriptionRepository;
import ru.practicum.ewm_service.repository.UserRepository;
import ru.practicum.ewm_service.service.SubscriptionService;

import javax.validation.ValidationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;


    private final RequestsRepository requestsRepository;

    @Override
    public void addRequestForFriendship(Long userId, Long friendId) {
        User requester = checkAndReturnUser(userId);
        List<Status> statuses = List.of(Status.PENDING, Status.REJECTED, Status.CONFIRMED);

        if (subscriptionRepository.existsByUserIdAndFriendIdAndStatusIn(userId, friendId, statuses))  {
            throw new ValidationException("Юзер уже подал заявку");
        }

        User maybeFriend = checkAndReturnUser(friendId);
        Subscription subscription = new Subscription();
        subscription.setUser(requester);
        subscription.setFriend(maybeFriend);
        subscription.setStatus(Status.PENDING);
        subscriptionRepository.save(subscription);

    }

    @Override
    public void canselRequestForFriendship(Long userId, Long friendId) {
        List<Status> statuses = List.of(Status.PENDING, Status.CONFIRMED);
        Subscription subscription = subscriptionRepository
                .findSubscriptionByUserIdAndFriendIdAndStatusIn(userId, friendId, statuses).orElseThrow(
                        () -> new IllegalArgumentException("Заявка на добавление в друзья не была направлена, " +
                                "отклонена или отменена самим пользователем"));

        subscription.setStatus(Status.CANCELED);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void confirmRequestForFriendship(Long userId, Long friendId) {
        List<Status> statuses = List.of(Status.PENDING, Status.REJECTED);
        Subscription subscription = subscriptionRepository
                .findByUserIdAndFriendIdAndStatusIn(friendId, userId, statuses).orElseThrow(
                        () -> new IllegalArgumentException("Заявка на добавление в друзья не была направлена," +
                                " отменена отправителем или уже одобрена"));
        subscription.setStatus(Status.CONFIRMED);
        subscriptionRepository.save(subscription);

    }

    @Override
    public void rejectRequestForFriendship(Long userId, Long friendId) {
        List<Status> statuses = List.of(Status.PENDING, Status.CONFIRMED);
        Subscription subscription = subscriptionRepository
                .findByUserIdAndFriendIdAndStatusIn(friendId, userId, statuses).orElseThrow(
                        () -> new IllegalArgumentException("Заявка на добавление в друзья не была направлена, " +
                                "отклонена или отменена самим пользователем"));
        subscription.setStatus(Status.REJECTED);
        subscriptionRepository.save(subscription);

    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getFriendById(Long userId, Long friendId) {

        User user = checkAndReturnUser(friendId);

        if (!subscriptionRepository
                .existsByUserIdAndFriendIdOrFriendIdAndUserIdAndStatus(
                        userId, friendId, userId, friendId, Status.CONFIRMED)) {

            throw new IllegalArgumentException("Заявка на добавление в друзья не была направлена, либо была отменена");

        }

        return UserMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllFriends(Long userId) {
        List<Subscription> subscriptions = subscriptionRepository
                .findSubscriptionByUserIdOrFriendIdAndStatus(userId, userId, Status.CONFIRMED);

        if (subscriptions.isEmpty()) {
            return Collections.emptyList();
        }

        List<User> friends = new ArrayList<>();

        subscriptions.forEach(s -> {

            if (!s.getUser().getId().equals(userId)) {
                friends.add(s.getUser());
            }

            if (!s.getFriend().getId().equals(userId)) {
                friends.add(s.getFriend());
            }

        });

        return friends.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShort> getEventsWithFriendsParticipation(Long userId, Long friendId, int from, int size) {
        checkUser(userId);
        List<Request> friendRequests = requestsRepository.findAllByRequesterIdAndStatus(
                friendId, Status.CONFIRMED, PageRequest.of(from, size));

        return friendRequests.stream()
                .map(Request::getEvent)
                .filter(e -> e.getEventDate().after(Timestamp.valueOf(LocalDateTime.now())))
                .map(EventMapper::toEventShort)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionDto> getAllWithParameters(
            List<Long> users, List<Long> friends, List<Status> statuses, int from, int size) {
        return subscriptionRepository
                .getAllWithParameters(users, friends, statuses, from, size)
                .stream()
                .map(SubscriptionMapper::toDto)
                .collect(Collectors.toList());
    }


    private User checkAndReturnUser(Long userId) {

        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Юзер с id " + userId + " не существует"));

    }

    private void checkUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Юзер с id " + userId + " не существует");
        }
    }

}
