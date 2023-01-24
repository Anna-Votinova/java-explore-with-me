package ru.practicum.stats_server.entity.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.stats_server.entity.Stats;
import ru.practicum.stats_server.entity.dto.EndpointHitDto;

import java.sql.Timestamp;
import java.time.Instant;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsMapper {

    public Stats fromDto(EndpointHitDto dto) {
        Stats stats =  new Stats();
        stats.setApp(dto.getApp());
        stats.setIp(dto.getIp());
        stats.setUri(dto.getUri());
        stats.setTimestamp(Timestamp.from(Instant.now()));

        return stats;
    }
}
