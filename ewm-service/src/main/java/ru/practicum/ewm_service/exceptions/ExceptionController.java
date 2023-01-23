package ru.practicum.ewm_service.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice("ru.practicum.ewm_service")
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleIllegalArgumentException(final IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Ошибка введенных данных: " + e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(final Throwable e) {
        log.error(e.getMessage(), e);
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Непредвиденная ошибка. " + e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUnknownStateException(final UnknownSortException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Unknown state: " + e.getMessage() + "Неизвестное состояние запроса")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final ValidationException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Ошибка введенных данных: " + e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleServletException(final ServletException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Пропущены обязательные параметры: " + e.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleThrowableDataIntegrityViolation(final DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Нарушение уникальности SQL-данных: " + e.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ExceptionHandler(DuplicateParameterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateParameterException(final DuplicateParameterException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Нарушение уникальности: " + e.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ExceptionHandler(AccessForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenException(final RuntimeException e) {
        log.error(e.getMessage(), e);
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Доступ запрещен: " + e.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onConstraintValidationException(final ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        final List<String> violations = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath().toString() + violation.getMessage())
                .collect(Collectors.toList());
        return ApiError.builder()
                .errors(violations)
                .reason("Ошибка валидации введенных данных: " + e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        final List<String> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + error.getDefaultMessage())
                .collect(Collectors.toList());
        return ApiError.builder()
                .errors(violations)
                .reason("Ошибка валидации введенных данных: " + e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
