package ru.practicum.ewm_service.service;

import ru.practicum.ewm_service.entity.dto.event.AdminUpdateEventRequest;
import ru.practicum.ewm_service.entity.dto.event.EventFullDto;
import ru.practicum.ewm_service.entity.dto.event.NewEventDto;
import ru.practicum.ewm_service.entity.dto.event.UpdateEventRequest;
import ru.practicum.ewm_service.entity.dto.request.ParticipationRequestDto;
import ru.practicum.ewm_service.entity.shorts.EventShort;
import ru.practicum.ewm_service.entity.util.SortEvent;
import ru.practicum.ewm_service.entity.util.State;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventFullDto createEvent(Long userId, NewEventDto dto);

    EventFullDto updateEvent(Long userId, UpdateEventRequest dto);

    EventFullDto rejectEventByOwner(Long userId, Long eventId);

    List<EventShort> getAllPageable(Long userId, int from, int size);

    EventFullDto getFullEventDtoById(Long userId, Long eventId);

    List<ParticipationRequestDto> findAllUserEventRequests(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    EventFullDto updateEventByAdmin(Long eventId, AdminUpdateEventRequest dto);

    List<EventFullDto> getAllWithParameters(
            List<Long> users, List<State> states, List<Long> categories,
            Timestamp rangeStart, Timestamp rangeEnd, int from, int size);

    EventFullDto getEvent(Long id);

    List<EventShort> getListEvents(
            String text, List<Long> categories, boolean paid,
            LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
            SortEvent sort, int from, int size);
}
