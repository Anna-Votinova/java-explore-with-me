package ru.practicum.ewm_service.service;

import ru.practicum.ewm_service.entity.dto.request.ParticipationRequestDto;

import java.util.List;


public interface RequestService {

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getAll(Long userId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
