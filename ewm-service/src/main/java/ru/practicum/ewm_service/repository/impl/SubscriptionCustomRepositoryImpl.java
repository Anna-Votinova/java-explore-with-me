package ru.practicum.ewm_service.repository.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm_service.entity.Subscription;
import ru.practicum.ewm_service.entity.User;
import ru.practicum.ewm_service.entity.util.Status;
import ru.practicum.ewm_service.repository.SubscriptionCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class SubscriptionCustomRepositoryImpl implements SubscriptionCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Subscription> getAllWithParameters(
            List<Long> users,
            List<Long> friends,
            List<Status> statuses,
            int from,
            int size
    ) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subscription> criteriaQuery = criteriaBuilder.createQuery(Subscription.class);
        Root<Subscription> subRoot = criteriaQuery.from(Subscription.class);

        List<Predicate> predicateList = new ArrayList<>();


        Optional.ofNullable(users).ifPresent(
                u -> predicateList.add(
                        criteriaBuilder.equal(subRoot.get("user"),
                                u.stream().map(User::new).collect(Collectors.toList()))
                )
        );

        Optional.ofNullable(friends).ifPresent(
                f -> predicateList.add(
                        criteriaBuilder.equal(subRoot.get("friend"),
                                f.stream().map(User::new).collect(Collectors.toList()))
                )
        );

        Optional.ofNullable(statuses).ifPresent(
                s -> predicateList.add(
                        criteriaBuilder.equal(subRoot.get("status"), s)
                )
        );


        criteriaQuery.select(subRoot).where(predicateList.toArray(new Predicate[]{}));

        return entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }
}
