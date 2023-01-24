package ru.practicum.ewm_service.service;

import ru.practicum.ewm_service.entity.dto.category.CategoryDto;
import ru.practicum.ewm_service.entity.dto.category.NewCategoryDto;

import java.util.List;


public interface CategoryService {

    CategoryDto addCategory(NewCategoryDto dto);

    CategoryDto updateCategory(CategoryDto dto);

    void deleteCategory(Long id);

    CategoryDto getCategory(Long catId);

    List<CategoryDto> getCategories(int from, int size);
}
