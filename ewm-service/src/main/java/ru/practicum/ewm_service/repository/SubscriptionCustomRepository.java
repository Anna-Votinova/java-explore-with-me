package ru.practicum.ewm_service.repository;

import ru.practicum.ewm_service.entity.Subscription;
import ru.practicum.ewm_service.entity.util.Status;

import java.util.List;

public interface SubscriptionCustomRepository {

    List<Subscription> getAllWithParameters(
            List<Long> users, List<Long> friends, List<Status> statuses, int from, int size);

}
