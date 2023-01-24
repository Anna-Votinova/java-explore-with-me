package ru.practicum.ewm_service.entity.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm_service.entity.dto.location.LocationDto;
import ru.practicum.ewm_service.entity.dto.category.CategoryDto;
import ru.practicum.ewm_service.entity.shorts.UserShort;
import ru.practicum.ewm_service.entity.util.State;

import java.sql.Timestamp;

import static ru.practicum.ewm_service.entity.util.Constants.DATE_TIME;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {

    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME)
    private Timestamp createdOn;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME)
    private Timestamp eventDate;

    private Long id;

    private UserShort initiator;

    private LocationDto location;

    private boolean paid;

    private int participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME)
    private Timestamp publishedOn;

    private boolean requestModeration;

    private State state;

    private String title;

    private Long views;
}
