package ru.practicum.ewm_service.repository;

import ru.practicum.ewm_service.entity.Event;
import ru.practicum.ewm_service.entity.util.SortEvent;
import ru.practicum.ewm_service.entity.util.State;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface EventCustomRepository {

    List<Event> getListEvents(String text, List<Long> categories, boolean paid,
                              LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
                              SortEvent sort, int from, int size);

    List<Event> getAllWithParameters(List<Long> users, List<State> states, List<Long> categories,
                                     Timestamp rangeStart, Timestamp rangeEnd, int from, int size);

}
