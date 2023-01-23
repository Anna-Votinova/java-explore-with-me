package ru.practicum.ewm_service.entity.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm_service.exceptions.ValidateDateEvent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {

    @Size(max = 2000, min = 20)
    @NotBlank
    private String annotation;
    @NotNull
    private Long category;
    @Size(max = 7000, min = 20)
    @NotBlank
    private String description;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ValidateDateEvent(message = "Дата и время события не могут быть раньше, чем через 2 часа от текущего момента")
    private Timestamp eventDate;
    @NotNull
    private Long eventId;

    private boolean paid;
    private int participantLimit;
    @Size(max = 120, min = 3)
    @NotBlank
    private String title;
}
