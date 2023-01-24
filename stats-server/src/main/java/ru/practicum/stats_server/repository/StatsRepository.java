package ru.practicum.stats_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stats_server.entity.Stats;

public interface StatsRepository extends JpaRepository<Stats, Long>, CustomStatsRepository {

}
