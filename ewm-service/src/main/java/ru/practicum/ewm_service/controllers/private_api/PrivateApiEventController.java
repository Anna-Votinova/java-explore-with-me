package ru.practicum.ewm_service.controllers.private_api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.entity.dto.event.EventFullDto;
import ru.practicum.ewm_service.entity.dto.event.NewEventDto;
import ru.practicum.ewm_service.entity.dto.event.UpdateEventRequest;
import ru.practicum.ewm_service.entity.dto.request.ParticipationRequestDto;
import ru.practicum.ewm_service.entity.shorts.EventShort;
import ru.practicum.ewm_service.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
@Validated
public class PrivateApiEventController {

    private final EventService eventService;

    @PostMapping
    public EventFullDto createEvent(@Positive @PathVariable Long userId, @Valid @RequestBody NewEventDto dto) {
        return eventService.createEvent(userId, dto);
    }

    @PatchMapping
    public EventFullDto updateEvent(@Positive @PathVariable Long userId, @Valid @RequestBody UpdateEventRequest dto) {
        return eventService.updateEvent(userId, dto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto rejectEventByOwner(@Positive @PathVariable Long userId, @Positive @PathVariable Long eventId) {
        return eventService.rejectEventByOwner(userId, eventId);
    }

    @GetMapping
    public List<EventShort> getAllPageable(@Positive @PathVariable Long userId,
                                           @PositiveOrZero @RequestParam(
                                                   name = "from", defaultValue = "0") final int from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") final int size) {
        return eventService.getAllPageable(userId, from, size);

    }

    @GetMapping("{eventId}")
    public EventFullDto getFullEventDtoById(@Positive @PathVariable Long userId, @Positive @PathVariable Long eventId) {
        return eventService.getFullEventDtoById(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> findAllUserEventRequests(@Positive @PathVariable Long userId,
                                                                  @Positive @PathVariable Long eventId) {
        return eventService.findAllUserEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@Positive @PathVariable Long userId,
                                                  @Positive @PathVariable Long eventId,
                                                  @Positive @PathVariable Long reqId) {
        return eventService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@Positive @PathVariable Long userId,
                                                 @Positive @PathVariable Long eventId,
                                                 @Positive @PathVariable Long reqId) {
        return eventService.rejectRequest(userId, eventId, reqId);
    }


}
