package ru.practicum.stats_server.repository;

import ru.practicum.stats_server.entity.dto.ViewStats;

import java.sql.Timestamp;
import java.util.List;

public interface CustomStatsRepository {

    List<ViewStats> getStats(Timestamp start, Timestamp end, List<String> uris, boolean unique);
}
