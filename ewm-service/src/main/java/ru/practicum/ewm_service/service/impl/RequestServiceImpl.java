package ru.practicum.ewm_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_service.entity.Event;
import ru.practicum.ewm_service.entity.Request;
import ru.practicum.ewm_service.entity.User;
import ru.practicum.ewm_service.entity.dto.request.ParticipationRequestDto;
import ru.practicum.ewm_service.entity.mapper.RequestMapper;
import ru.practicum.ewm_service.entity.util.State;
import ru.practicum.ewm_service.entity.util.Status;
import ru.practicum.ewm_service.repository.EventRepository;
import ru.practicum.ewm_service.repository.RequestsRepository;
import ru.practicum.ewm_service.repository.UserRepository;
import ru.practicum.ewm_service.service.RequestService;

import javax.validation.ValidationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestsRepository requestsRepository;
    private final EventRepository eventRepository;

    private final UserRepository userRepository;


    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Event event = checkAndReturnEvent(eventId);
        User user = checkAndReturnUser(userId);
        if (requestsRepository.existsByEventIdAndRequesterId(eventId, userId)
                || event.getInitiator().getId().equals(userId)
                || !event.getState().equals(State.PUBLISHED)
                || event.getParticipantLimit() != 0 && event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new ValidationException("Запрос не может быть добавлен");
        }

        Request request = new Request();
        request.setCreated(Timestamp.valueOf(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        request.setEvent(event);
        request.setRequester(user);
        if (event.isRequestModeration()) {
            request.setStatus(Status.PENDING);
        } else {
            request.setStatus(Status.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1L);
            eventRepository.save(event);
        }

        return RequestMapper.toDto(requestsRepository.save(request));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getAll(Long userId) {
        checkUser(userId);
        return requestsRepository.findAllByRequesterId(userId)
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        Request request = requestsRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(
                () -> new IllegalArgumentException("Запрос не найден"));
        if (request.getStatus().equals(Status.CONFIRMED)) {
        Event event = checkAndReturnEvent(request.getEvent().getId());
        event.setConfirmedRequests(event.getConfirmedRequests() - 1L);
        eventRepository.save(event);
        }
        request.setStatus(Status.CANCELED);
        return RequestMapper.toDto(requestsRepository.save(request));
    }

    private Event checkAndReturnEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Event  с id " + id + " не существует"));
    }

    private User checkAndReturnUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Юзер с id " + userId + " не существует"));
    }

    private void checkUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Юзер с id " + userId + " не существует");
        }
    }
}
