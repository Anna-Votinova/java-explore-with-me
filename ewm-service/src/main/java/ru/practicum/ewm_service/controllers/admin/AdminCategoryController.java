package ru.practicum.ewm_service.controllers.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.entity.dto.category.CategoryDto;
import ru.practicum.ewm_service.entity.dto.category.NewCategoryDto;
import ru.practicum.ewm_service.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
@Validated
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto addCategory(@Valid @RequestBody NewCategoryDto dto) {
        return categoryService.addCategory(dto);
    }

    @PatchMapping
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto dto) {
        return categoryService.updateCategory(dto);
    }

    @DeleteMapping("{catId}")
    public void deleteCategory(@Positive @PathVariable final Long catId) {
        categoryService.deleteCategory(catId);
    }
}
