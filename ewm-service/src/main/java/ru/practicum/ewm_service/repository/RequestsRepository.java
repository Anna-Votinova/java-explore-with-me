package ru.practicum.ewm_service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm_service.entity.Request;
import ru.practicum.ewm_service.entity.util.Status;

import java.util.List;
import java.util.Optional;

public interface RequestsRepository extends JpaRepository<Request, Long> {

    boolean existsByEventIdAndRequesterId(Long eventId, Long userId);

    List<Request> findRequestsByEventId(Long eventId);

    List<Request> findAllByRequesterId(Long userId);

    Optional<Request> findByIdAndRequesterId(Long requestId, Long userId);

    Optional<Request> findByIdAndEventId(Long reqId, Long eventId);

    List<Request> findAllByRequesterIdAndStatus(Long friendId, Status status, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Request r set r.status = ?1 where r.event = ?2 and r.status = ?3")
    void setRequestCancelStatus(Status statusCancel, Long eventId, Status statusPending);
}
