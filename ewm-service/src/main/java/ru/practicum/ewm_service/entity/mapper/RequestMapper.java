package ru.practicum.ewm_service.entity.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm_service.entity.Request;
import ru.practicum.ewm_service.entity.dto.request.ParticipationRequestDto;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static ParticipationRequestDto toDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated().toLocalDateTime())
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }
}
