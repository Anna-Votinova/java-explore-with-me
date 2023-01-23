package ru.practicum.ewm_service.entity.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewm_service.entity.util.Status;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private Long event;
    private Long id;

    private Long requester;

    private Status status;


}
