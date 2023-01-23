package ru.practicum.stats_server.repository.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.stats_server.entity.Stats;
import ru.practicum.stats_server.entity.dto.ViewStats;
import ru.practicum.stats_server.repository.CustomStatsRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class CustomStatsRepositoryImpl implements CustomStatsRepository {

    private final EntityManager entityManager;

    @Override
    public List<ViewStats> getStats(Timestamp start, Timestamp end, List<String> uris, boolean unique) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ViewStats> criteriaQuery = criteriaBuilder.createQuery(ViewStats.class);
        Root<Stats> statsRoot = criteriaQuery.from(Stats.class);

        List<Predicate> predicateList = new ArrayList<>();

        criteriaQuery.select(criteriaBuilder.construct(ViewStats.class,
                statsRoot.get("uri"),
                statsRoot.get("app"),
                unique ? criteriaBuilder.countDistinct(statsRoot.get("ip")) : criteriaBuilder.count(statsRoot.get("ip"))
                )
        );

        criteriaQuery.groupBy(
                statsRoot.get("app"),
                statsRoot.get("uri"),
                statsRoot.get("ip")
        );

        predicateList.add(criteriaBuilder.between(
                statsRoot.get("timestamp"),
                start,
                end)
        );

        Optional.ofNullable(uris).ifPresent(
                u -> predicateList.add(criteriaBuilder.equal(statsRoot.get("uri"), u)));

        criteriaQuery.where(predicateList.toArray(new Predicate[]{}));

        return entityManager.createQuery(criteriaQuery).getResultList();

    }
}


