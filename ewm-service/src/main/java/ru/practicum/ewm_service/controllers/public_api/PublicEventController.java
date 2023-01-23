package ru.practicum.ewm_service.controllers.public_api;


import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.client.StatsClient;
import ru.practicum.ewm_service.entity.dto.event.EventFullDto;
import ru.practicum.ewm_service.entity.shorts.EventShort;
import ru.practicum.ewm_service.entity.util.SortEvent;
import ru.practicum.ewm_service.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
@Validated
public class PublicEventController {

    private final EventService eventService;
    private final StatsClient statsClient;

    @GetMapping("/{id}")
    public EventFullDto getEvent(@Positive @PathVariable Long id,
                                 HttpServletRequest request) {
        statsClient.createHit(request);
        return eventService.getEvent(id);
    }

    @GetMapping
    public List<EventShort> getListEvents(@RequestParam (required = false) String text,
                                          @RequestParam (required = false) List<Long> categories,
                                          @RequestParam (required = false) boolean paid,
                                          @RequestParam (required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                          @RequestParam (required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                          @RequestParam (defaultValue = "false", required = false) boolean onlyAvailable,
                                          @RequestParam (required = false) SortEvent sort,
                                          @PositiveOrZero @RequestParam (defaultValue = "0", required = false) int from,
                                          @Positive @RequestParam (defaultValue = "10", required = false) int size,
                                          HttpServletRequest request) {
        statsClient.createHit(request);
        return eventService.getListEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }
}
