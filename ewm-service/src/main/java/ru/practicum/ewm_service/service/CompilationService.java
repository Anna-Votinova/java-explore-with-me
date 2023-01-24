package ru.practicum.ewm_service.service;

import ru.practicum.ewm_service.entity.dto.compilation.CompilationDto;
import ru.practicum.ewm_service.entity.dto.compilation.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto dto);

    void addEventToCompilation(Long compId, Long eventId);

    void deleteCompilation(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void pinCompilation(Long compId);

    void unPinCompilation(Long compId);

    CompilationDto findCompilation(Long compId);

    List<CompilationDto> findCompilations(boolean pinned, int from, int size);
}
