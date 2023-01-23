package ru.practicum.ewm_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_service.entity.*;
import ru.practicum.ewm_service.entity.dto.event.AdminUpdateEventRequest;
import ru.practicum.ewm_service.entity.dto.event.EventFullDto;
import ru.practicum.ewm_service.entity.dto.event.NewEventDto;
import ru.practicum.ewm_service.entity.dto.event.UpdateEventRequest;
import ru.practicum.ewm_service.entity.dto.request.ParticipationRequestDto;
import ru.practicum.ewm_service.entity.mapper.EventMapper;
import ru.practicum.ewm_service.entity.mapper.LocationMapper;
import ru.practicum.ewm_service.entity.mapper.RequestMapper;
import ru.practicum.ewm_service.entity.shorts.EventShort;
import ru.practicum.ewm_service.entity.util.SortEvent;
import ru.practicum.ewm_service.entity.util.State;
import ru.practicum.ewm_service.entity.util.Status;
import ru.practicum.ewm_service.exceptions.AccessForbiddenException;
import ru.practicum.ewm_service.repository.*;
import ru.practicum.ewm_service.service.EventService;


import javax.validation.ValidationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final LocationRepository locationRepository;

    private final RequestsRepository requestsRepository;

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto dto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Юзер с id " + userId + " не существует"));
        Category category = checkAndReturnCategory(dto.getCategory());
        Location location = locationRepository.save(LocationMapper.fromDto(dto.getLocation()));
        Event event = EventMapper.fromDto(dto);
        event.setInitiator(user);
        event.setLocation(location);
        event.setCategory(category);

        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto updateEvent(Long userId, UpdateEventRequest dto) {
        final Event eventForUpdate = eventRepository.findByIdAndInitiatorIdAndStateNot(
                dto.getEventId(), userId, State.PUBLISHED).orElseThrow(
                        () -> new IllegalArgumentException(
                                "Событие с id " + dto.getEventId() + " недоступно для редактирования"));
        if (eventForUpdate.getState().equals(State.CANCELED)) {
            eventForUpdate.setState(State.PENDING);
        }
        Optional.ofNullable(dto.getAnnotation()).ifPresent(eventForUpdate::setAnnotation);
        Optional.ofNullable(dto.getCategory()).ifPresent(id -> eventForUpdate.setCategory(
                categoryRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("Такой категории не существует"))));
        Optional.ofNullable(dto.getDescription()).ifPresent(eventForUpdate::setDescription);
        Optional.ofNullable(dto.getEventDate()).ifPresent(eventForUpdate::setEventDate);
        Optional.of(dto.isPaid()).ifPresent(eventForUpdate::setPaid);
        Optional.of(dto.getParticipantLimit()).ifPresent(eventForUpdate::setParticipantLimit);
        Optional.ofNullable(dto.getTitle()).ifPresent(eventForUpdate::setTitle);
        return EventMapper.toDto(eventRepository.save(eventForUpdate));
    }

    @Override
    public EventFullDto rejectEventByOwner(Long userId, Long eventId) {
        checkUser(userId);
        Event event = eventRepository.findByIdAndInitiatorIdAndState(
                eventId, userId, State.PENDING).orElseThrow(
                        () -> new IllegalArgumentException(
                "Событие с id " + eventId + " недоступно для редактирования"));
        event.setState(State.CANCELED);
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShort> getAllPageable(Long userId, int from, int size) {

        Pageable pageable = PageRequest.of(from, size);
        Page<EventShort> shots = eventRepository.findAllByInitiatorId(userId, pageable);

        return shots
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getFullEventDtoById(Long userId, Long eventId) {
        checkUser(userId);
        Event event = eventRepository.findByIdAndInitiatorId(
                eventId, userId).orElseThrow(
                () -> new IllegalArgumentException("Событие с id " + eventId + " не найдено"));
        return EventMapper.toDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> findAllUserEventRequests(Long userId, Long eventId) {
        checkUser(userId);
        List<Request> requests = requestsRepository.findRequestsByEventId(eventId);

        return requests.stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        checkUser(userId);
        Event event = checkAndReturnEvent(eventId);
        Request request = checkAndReturnRequest(reqId);

        if (!event.isRequestModeration() || event.getParticipantLimit() == 0
                || event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new AccessForbiddenException("Ошибка при подтверждении запроса");
        }

        if (event.getConfirmedRequests() + 1 == event.getParticipantLimit()) {
            requestsRepository.setRequestCancelStatus(Status.CANCELED, eventId, Status.PENDING);
        }

        request.setStatus(Status.CONFIRMED);
        event.setConfirmedRequests(event.getConfirmedRequests() + 1L);
        eventRepository.save(event);

        return RequestMapper.toDto(requestsRepository.save(request));
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        checkUser(userId);
        Request request = requestsRepository.findByIdAndEventId(reqId,eventId).orElseThrow(() ->
                new IllegalArgumentException("Request  с id " + eventId + " не существует"));
        request.setStatus(Status.REJECTED);
        return RequestMapper.toDto(requestsRepository.save(request));
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        Event event = checkAndReturnEvent(eventId);

        LocalDateTime currentTime = LocalDateTime.now();

        if (event.getEventDate().before(Timestamp.valueOf(currentTime.plusHours(1)))
                || !event.getState().equals(State.PENDING)) {
            throw new ValidationException("Дата начала события должна быть не ранее чем за час от даты публикации. " +
                    "И событие должно быть в состоянии ожидания публикации");
        }

        event.setState(State.PUBLISHED);
        event.setPublishedOn(Timestamp.valueOf(currentTime));
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        Event event = checkAndReturnEvent(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("Событие не должно быть опубликовано.");
        }
        event.setState(State.CANCELED);
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, AdminUpdateEventRequest dto) {
        Event eventForUpdate = checkAndReturnEvent(eventId);
        Optional.ofNullable(dto.getTitle()).ifPresent(eventForUpdate::setTitle);
        Optional.ofNullable(dto.getAnnotation()).ifPresent(eventForUpdate::setAnnotation);
        Optional.ofNullable(dto.getCategory()).ifPresent(id -> eventForUpdate.setCategory(
                categoryRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("Такой категории не существует"))));
        Optional.ofNullable(dto.getDescription()).ifPresent(eventForUpdate::setDescription);
        Optional.ofNullable(dto.getEventDate()).ifPresent(eventForUpdate::setEventDate);
        Optional.of(dto.isPaid()).ifPresent(eventForUpdate::setPaid);
        Optional.of(dto.getParticipantLimit()).ifPresent(eventForUpdate::setParticipantLimit);
        Optional.ofNullable(dto.getLocation()).ifPresent(l -> eventForUpdate.setLocation(LocationMapper.fromDto(l)));
        Optional.of(dto.isRequestModeration()).ifPresent(eventForUpdate::setRequestModeration);
        return EventMapper.toDto(eventRepository.save(eventForUpdate));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getAllWithParameters(
            List<Long> users, List<State> states, List<Long> categories,
            Timestamp rangeStart, Timestamp rangeEnd, int from, int size) {

        List<Event> events = eventRepository.getAllWithParameters(
                users, states, categories, rangeStart, rangeEnd, from, size
        );

        return events.stream()
                .map(EventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEvent(Long id) {
        Event event = eventRepository.findByIdAndState(id, State.PUBLISHED).orElseThrow(
                () -> new IllegalArgumentException("Event  с id " + id + " не существует"));
        event.setViews(event.getViews() + 1);
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public List<EventShort> getListEvents(
            String text, List<Long> categories, boolean paid,
            LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
            SortEvent sort, int from, int size) {

       List<Event> finalEventList = eventRepository.getListEvents(
               text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
               sort, from, size);

       finalEventList.forEach(e -> {
           e.setViews(e.getViews() + 1L);
           eventRepository.save(e);
       });

        return finalEventList.stream().map(EventMapper::toEventShort).collect(Collectors.toList());
    }


    private void checkUser(Long userId) {
       if (!userRepository.existsById(userId)) {
           throw new IllegalArgumentException("Юзер с id " + userId + " не существует");
       }
    }

    private Category checkAndReturnCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Категория с id " + id + " не существует"));
    }

    private Event checkAndReturnEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Event  с id " + id + " не существует"));
    }

    private Request checkAndReturnRequest(Long id) {
        return requestsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Request  с id " + id + " не существует"));
    }

}
