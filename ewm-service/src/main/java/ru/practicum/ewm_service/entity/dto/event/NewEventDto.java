package ru.practicum.ewm_service.entity.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm_service.entity.dto.location.LocationDto;
import ru.practicum.ewm_service.exceptions.ValidateDateEvent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static ru.practicum.ewm_service.entity.util.Constants.DATE_TIME;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {

    @Size(max = 2000, min = 20)
    @NotBlank
    private String annotation;
    @NotNull
    private Long category;
    @Size(max = 7000, min = 20)
    @NotBlank
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME)
    @ValidateDateEvent(message = "Дата и время события не могут быть раньше, чем через 2 часа от текущего момента")
    private Timestamp eventDate;
    @NotNull
    private LocationDto location;

    @Builder.Default
    private boolean paid = false;
    private int participantLimit;
    private boolean requestModeration;
    @Size(max = 120, min = 3)
    @NotBlank
    private String title;

}
