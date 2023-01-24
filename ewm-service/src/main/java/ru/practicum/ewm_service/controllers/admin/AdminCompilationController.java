package ru.practicum.ewm_service.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.entity.dto.compilation.CompilationDto;
import ru.practicum.ewm_service.entity.dto.compilation.NewCompilationDto;
import ru.practicum.ewm_service.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
@Validated
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto dto) {
        return compilationService.createCompilation(dto);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@Positive @PathVariable Long compId,
                                      @Positive @PathVariable Long eventId) {
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@Positive @PathVariable Long compId) {
        compilationService.deleteCompilation(compId);

    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@Positive @PathVariable Long compId,
                                           @Positive @PathVariable Long eventId) {
        compilationService.deleteEventFromCompilation(compId, eventId);

    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@Positive @PathVariable Long compId) {
        compilationService.pinCompilation(compId);

    }

    @DeleteMapping("/{compId}/pin")
    public void unPinCompilation(@Positive @PathVariable Long compId) {
        compilationService.unPinCompilation(compId);

    }
}
