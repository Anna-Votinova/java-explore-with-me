package ru.practicum.stats_server.service;

import ru.practicum.stats_server.entity.dto.EndpointHitDto;
import ru.practicum.stats_server.entity.dto.ViewStats;

import java.sql.Timestamp;
import java.util.List;

public interface StatsService {

    void saveHit(EndpointHitDto dto);

    List<ViewStats> getStats(Timestamp start, Timestamp end, List<String> uris, boolean unique);
}
