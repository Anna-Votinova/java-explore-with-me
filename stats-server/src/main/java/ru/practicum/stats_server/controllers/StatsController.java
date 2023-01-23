package ru.practicum.stats_server.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats_server.entity.dto.EndpointHitDto;
import ru.practicum.stats_server.entity.dto.ViewStats;
import ru.practicum.stats_server.service.StatsService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Validated
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    public void saveHit(@RequestBody @Valid EndpointHitDto dto) {
        service.saveHit(dto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam Timestamp start,
                                    @RequestParam Timestamp end,
                                    @RequestParam (required = false) List<String> uris,
                                    @RequestParam (defaultValue = "false") boolean unique) {
        return service.getStats(start, end, uris, unique);
    }

}
