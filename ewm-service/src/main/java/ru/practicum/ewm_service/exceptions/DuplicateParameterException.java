package ru.practicum.ewm_service.exceptions;

public class DuplicateParameterException extends RuntimeException {

    public DuplicateParameterException(String message) {
        super(message);
    }
}
