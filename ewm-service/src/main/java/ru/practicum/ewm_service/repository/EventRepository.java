package ru.practicum.ewm_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_service.entity.Event;
import ru.practicum.ewm_service.entity.shorts.EventShort;
import ru.practicum.ewm_service.entity.util.State;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, EventCustomRepository {

    boolean existsByCategoryId(Long id);

    Optional<Event> findByIdAndInitiatorIdAndStateNot(Long id, Long userId, State state);

    Optional<Event> findByIdAndInitiatorIdAndState(Long id, Long userId, State state);

    Page<EventShort> findAllByInitiatorId(Long userId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long id, Long userId);

    Optional<Event> findByIdAndState(Long id, State state);

}
