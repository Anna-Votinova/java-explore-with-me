package ru.practicum.ewm_service.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.entity.dto.event.AdminUpdateEventRequest;
import ru.practicum.ewm_service.entity.dto.event.EventFullDto;
import ru.practicum.ewm_service.entity.util.State;
import ru.practicum.ewm_service.service.EventService;

import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
@Validated
public class AdminEventController {

    private final EventService eventService;

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@Positive @PathVariable Long eventId) {
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@Positive @PathVariable Long eventId) {
        return eventService.rejectEvent(eventId);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@Positive @PathVariable Long eventId,
                                           @RequestBody AdminUpdateEventRequest dto) {
        return eventService.updateEventByAdmin(eventId,dto);
    }

    @GetMapping
    public List<EventFullDto> getAllWithParameters(
            @RequestParam (required = false) List<Long> users,
            @RequestParam (required = false) List<State> states,
            @RequestParam (required = false) List<Long> categories,
            @RequestParam (required = false) Timestamp rangeStart,
            @RequestParam (required = false) Timestamp rangeEnd,
            @RequestParam(name = "from", defaultValue = "0", required = false) final int from,
            @RequestParam(name = "size", defaultValue = "10", required = false) final int size) {
        return eventService.getAllWithParameters(users, states, categories, rangeStart, rangeEnd, from, size);
    }



}
