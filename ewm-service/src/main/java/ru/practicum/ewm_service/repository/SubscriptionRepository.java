package ru.practicum.ewm_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_service.entity.Subscription;
import ru.practicum.ewm_service.entity.util.Status;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, SubscriptionCustomRepository {

    Optional<Subscription> findSubscriptionByUserIdAndFriendIdAndStatusIn(
            Long userId, Long friendId, List<Status> statuses);

    Optional<Subscription> findByUserIdAndFriendIdAndStatusIn(
            Long friendId, Long userId, List<Status> statuses);

    boolean existsByUserIdAndFriendIdOrFriendIdAndUserIdAndStatus(
            Long userId, Long friendId, Long userId1, Long friendId1, Status status);

    List<Subscription> findSubscriptionByUserIdOrFriendIdAndStatus(Long userId, Long userId1, Status status);
}
