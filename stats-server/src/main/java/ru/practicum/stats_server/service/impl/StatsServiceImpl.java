package ru.practicum.stats_server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats_server.entity.dto.EndpointHitDto;
import ru.practicum.stats_server.entity.dto.ViewStats;
import ru.practicum.stats_server.entity.mapper.StatsMapper;
import ru.practicum.stats_server.repository.StatsRepository;
import ru.practicum.stats_server.service.StatsService;

import java.sql.Timestamp;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    private final StatsMapper mapper;

    @Override
    public void saveHit(EndpointHitDto dto) {
        repository.save(mapper.fromDto(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStats> getStats(Timestamp start, Timestamp end, List<String> uris, boolean unique) {

        return repository.getStats(start, end, uris, unique);
    }
}
