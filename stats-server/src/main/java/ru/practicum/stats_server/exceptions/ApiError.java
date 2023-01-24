package ru.practicum.stats_server.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.List;

import static ru.practicum.stats_server.Constants.DATE_TIME;

@Builder
@Getter
@Setter
@ToString
public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    @DateTimeFormat(pattern = DATE_TIME)
    private Timestamp timestamp;
}
