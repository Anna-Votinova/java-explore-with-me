package ru.practicum.ewm_service.entity.dto.hit;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

import static ru.practicum.ewm_service.entity.util.Constants.DATE_TIME;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHitDto {

    private Long id;
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME)
    private Timestamp timestamp;
}
