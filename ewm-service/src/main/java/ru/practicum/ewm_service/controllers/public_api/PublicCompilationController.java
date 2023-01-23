package ru.practicum.ewm_service.controllers.public_api;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.entity.dto.compilation.CompilationDto;
import ru.practicum.ewm_service.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
@Validated
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping("{compId}")
    public CompilationDto findCompilation(@Positive @PathVariable Long compId) {
        return compilationService.findCompilation(compId);
    }

    @GetMapping
    public List<CompilationDto> findCompilations(
            @RequestParam (required = false) boolean pinned,
            @PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
            @Positive @RequestParam (defaultValue = "10", required = false) int size) {
        return compilationService.findCompilations(pinned, from, size);
    }

}
