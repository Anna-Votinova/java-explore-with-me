package ru.practicum.ewm_service.exceptions;

public class UnknownSortException extends RuntimeException {

    public UnknownSortException(String state) {
        super(state);
    }
}
