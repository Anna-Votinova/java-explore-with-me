package ru.practicum.ewm_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_service.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
