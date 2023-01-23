package ru.practicum.ewm_service.entity.mapper;

import ru.practicum.ewm_service.entity.Category;
import ru.practicum.ewm_service.entity.Event;
import ru.practicum.ewm_service.entity.dto.category.CategoryDto;
import ru.practicum.ewm_service.entity.dto.event.EventFullDto;
import ru.practicum.ewm_service.entity.dto.event.NewEventDto;
import ru.practicum.ewm_service.entity.dto.location.LocationDto;
import ru.practicum.ewm_service.entity.shorts.EventShort;
import ru.practicum.ewm_service.entity.shorts.UserShort;
import ru.practicum.ewm_service.entity.util.State;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EventMapper {

    public static Event fromDto(NewEventDto dto) {

        Event event = new Event();
        event.setAnnotation(dto.getAnnotation());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        event.setState(State.PENDING);

        if (dto.isPaid()) {
            event.setPaid(true);
        }

        if (dto.getParticipantLimit() != 0) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }

        if (!dto.isRequestModeration()) {
            event.setRequestModeration(false);
        }

        event.setTitle(dto.getTitle());

        return event;

    }

    public static EventFullDto toDto(Event event) {
        UserShort userShort = new UserShort() {
            @Override
            public Long getId() {
                return event.getInitiator().getId();
            }

            @Override
            public String getName() {
                return event.getInitiator().getName();
            }
        };
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(userShort)
                .location(LocationDto.builder()
                        .lat(event.getLocation().getLat())
                        .lon(event.getLocation().getLon())
                        .build())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventShort toEventShort(Event event) {

        UserShort userShort = new UserShort() {
            @Override
            public Long getId() {
                return event.getInitiator().getId();
            }

            @Override
            public String getName() {
                return event.getInitiator().getName();
            }
        };

        return new EventShort() {
            @Override
            public String getAnnotation() {
                return event.getAnnotation();
            }

            @Override
            public Category getCategory() {
                return event.getCategory();
            }

            @Override
            public Long getConfirmedRequests() {
                return event.getConfirmedRequests();
            }

            @Override
            public Timestamp getEventDate() {
                return event.getEventDate();
            }

            @Override
            public Long getId() {
                return event.getId();
            }

            @Override
            public UserShort getInitiator() {
                return userShort;
            }

            @Override
            public boolean isPaid() {
                return event.isPaid();
            }

            @Override
            public String getTitle() {
                return event.getTitle();
            }

            @Override
            public Long getViews() {
                return event.getViews();
            }
        };

    }
}
