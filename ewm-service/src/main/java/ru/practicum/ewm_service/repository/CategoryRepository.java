package ru.practicum.ewm_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_service.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

}
