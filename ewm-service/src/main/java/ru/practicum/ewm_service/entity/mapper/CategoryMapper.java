package ru.practicum.ewm_service.entity.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm_service.entity.Category;
import ru.practicum.ewm_service.entity.dto.category.CategoryDto;
import ru.practicum.ewm_service.entity.dto.category.NewCategoryDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static Category fromDto(NewCategoryDto dto) {
        return new Category(dto.getName());
    }

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
