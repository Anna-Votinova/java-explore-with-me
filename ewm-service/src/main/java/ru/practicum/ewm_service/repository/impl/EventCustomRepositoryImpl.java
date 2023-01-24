package ru.practicum.ewm_service.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm_service.entity.Category;
import ru.practicum.ewm_service.entity.Event;
import ru.practicum.ewm_service.entity.User;
import ru.practicum.ewm_service.entity.util.SortEvent;
import ru.practicum.ewm_service.entity.util.State;
import ru.practicum.ewm_service.repository.EventCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class EventCustomRepositoryImpl implements EventCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Event> getListEvents(
            String text,
            List<Long> categories,
            boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            boolean onlyAvailable,
            SortEvent sort,
            int from,
            int size
    ) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> eventRoot = criteriaQuery.from(Event.class);

        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(criteriaBuilder.equal(eventRoot.get("state"), State.PUBLISHED));
        predicateList.add(criteriaBuilder.equal(eventRoot.get("paid"), paid));

        if (text != null) {
            predicateList.add(
                    criteriaBuilder.or(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(eventRoot.get("annotation")),
                                    "%" + text.toLowerCase() + "%"
                                    ),
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(eventRoot.get("description")),
                                    "%" + text.toLowerCase() + "%"
                            )
                    )
            );
        }

        Optional.ofNullable(categories).ifPresent(
                c -> predicateList.add(
                        criteriaBuilder.equal(eventRoot.get("category"),
                        c.stream().map(Category::new).collect(Collectors.toList()))
                )
        );

        if (onlyAvailable) {
            predicateList.add(
                    criteriaBuilder.lt(
                            eventRoot.get("confirmedRequests"), eventRoot.get("participantLimit")
                    )
            );
        }

        if (sort != null && sort.equals(SortEvent.EVENT_DATE)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(eventRoot.get("eventDate")));
        } else if (sort != null && sort.equals(SortEvent.VIEWS)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(eventRoot.get("views")));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(eventRoot.get("id")));
        }

        if (rangeStart == null || rangeEnd == null) {
            predicateList.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            eventRoot.get("eventDate"), Timestamp.from(Instant.now())
                    )
            );
        } else {
            predicateList.add(
                    criteriaBuilder.between(
                            eventRoot.get("eventDate"), Timestamp.valueOf(rangeStart), Timestamp.valueOf(rangeEnd)
                    )
            );
        }

        criteriaQuery.select(eventRoot).where(predicateList.toArray(new Predicate[]{}));


        return entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Event> getAllWithParameters(
            List<Long> users,
            List<State> states,
            List<Long> categories,
            Timestamp rangeStart,
            Timestamp rangeEnd,
            int from,
            int size
    ) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> eventRoot = criteriaQuery.from(Event.class);

        List<Predicate> predicateList = new ArrayList<>();


        Optional.ofNullable(users).ifPresent(
                u -> predicateList.add(
                        criteriaBuilder.equal(eventRoot.get("initiator"),
                                u.stream().map(User::new).collect(Collectors.toList()))
                )
        );

        Optional.ofNullable(states).ifPresent(
                s -> predicateList.add(
                        criteriaBuilder.equal(eventRoot.get("state"), s)
                )
        );

        Optional.ofNullable(categories).ifPresent(
                c -> predicateList.add(
                        criteriaBuilder.equal(eventRoot.get("category"),
                                c.stream().map(Category::new).collect(Collectors.toList()))
                )
        );


        if (rangeStart == null || rangeEnd == null) {
            predicateList.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            eventRoot.get("eventDate"), Timestamp.from(Instant.now())
                    )
            );
        }

        if (rangeStart != null) {
            predicateList.add(criteriaBuilder.greaterThan(eventRoot.get("eventDate"), rangeStart));
        }

        if (rangeEnd != null) {
            predicateList.add(criteriaBuilder.lessThan(eventRoot.get("eventDate"), rangeEnd));
        }

        criteriaQuery.select(eventRoot).where(predicateList.toArray(new Predicate[]{}));


        return entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }

}
