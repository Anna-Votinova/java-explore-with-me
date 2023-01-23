package ru.practicum.ewm_service.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm_service.entity.util.Constants.DATE_TIME;

@Builder
@Getter
@Setter
@ToString
public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME)
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
}
