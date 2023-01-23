package ru.practicum.ewm_service.controllers.public_api;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.entity.dto.category.CategoryDto;
import ru.practicum.ewm_service.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
@Validated
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping("{catId}")
    public CategoryDto getCategory(@Positive @PathVariable Long catId) {
        return categoryService.getCategory(catId);
    }

    @GetMapping
    public List<CategoryDto> getCategories(@PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
                                           @Positive @RequestParam (defaultValue = "10", required = false) int size) {
        return categoryService.getCategories(from, size);
    }
}
