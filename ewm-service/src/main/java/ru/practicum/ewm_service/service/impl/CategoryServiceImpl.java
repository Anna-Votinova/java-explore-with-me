package ru.practicum.ewm_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_service.entity.Category;
import ru.practicum.ewm_service.entity.dto.category.CategoryDto;
import ru.practicum.ewm_service.entity.dto.category.NewCategoryDto;
import ru.practicum.ewm_service.entity.mapper.CategoryMapper;
import ru.practicum.ewm_service.exceptions.DuplicateParameterException;
import ru.practicum.ewm_service.repository.CategoryRepository;
import ru.practicum.ewm_service.repository.EventRepository;
import ru.practicum.ewm_service.service.CategoryService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto dto) {
        checkCategoryName(dto.getName());
        return CategoryMapper.toDto(categoryRepository.save(CategoryMapper.fromDto(dto)));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto dto) {
        checkCategoryName(dto.getName());
        Category category = categoryRepository.findById(dto.getId()).orElseThrow(
                () -> new IllegalArgumentException("Катогория с id=" + dto.getId() + " не найдена"));
        category.setName(dto.getName());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        if (eventRepository.existsByCategoryId(id)) {
            throw new RuntimeException("Запрос приводит к нарушению целостности данных");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(
                () -> new IllegalArgumentException("Category with id=" + catId + " was not found"));
        return CategoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {

        Page<Category> categories;
        Pageable pageable = PageRequest.of(from, size);
        categories = categoryRepository.findAll(pageable);
        return categories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    private void checkCategoryName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new DuplicateParameterException("Категория с " + name + "уже существует");
        }
    }

}
