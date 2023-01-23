package ru.practicum.ewm_service.entity.mapper;

import ru.practicum.ewm_service.entity.Compilation;
import ru.practicum.ewm_service.entity.dto.compilation.CompilationDto;
import ru.practicum.ewm_service.entity.dto.compilation.NewCompilationDto;

import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation fromDto(NewCompilationDto dto) {
        Compilation compilation = new Compilation();
        compilation.setTitle(dto.getTitle());
        compilation.setPinned(dto.isPinned());

        return compilation;
    }

    public static CompilationDto toDto(Compilation compilation) {

        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(compilation.getEvents()
                        .stream()
                        .map(EventMapper::toEventShort)
                        .collect(Collectors.toList()))
                .build();
    }


}
