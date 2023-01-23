package ru.practicum.ewm_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_service.entity.Compilation;
import ru.practicum.ewm_service.entity.Event;
import ru.practicum.ewm_service.entity.dto.compilation.CompilationDto;
import ru.practicum.ewm_service.entity.dto.compilation.NewCompilationDto;
import ru.practicum.ewm_service.entity.mapper.CompilationMapper;
import ru.practicum.ewm_service.repository.CompilationRepository;
import ru.practicum.ewm_service.repository.EventRepository;
import ru.practicum.ewm_service.service.CompilationService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    private final EventRepository eventRepository;

    @Override
    public CompilationDto createCompilation(NewCompilationDto dto) {
        Compilation compilation = CompilationMapper.fromDto(dto);
        compilation.setEvents(eventRepository.findAllById(dto.getEvents()));
        return CompilationMapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = getCompilation(compId);
        Event event = getEvent(eventId);
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);


    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = getCompilation(compId);
        Event event = getEvent(eventId);
        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);

    }

    @Override
    public void pinCompilation(Long compId) {
        Compilation compilation = getCompilation(compId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);

    }

    @Override
    public void unPinCompilation(Long compId) {
        Compilation compilation = getCompilation(compId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public CompilationDto findCompilation(Long compId) {
        return CompilationMapper.toDto(getCompilation(compId));
    }

    @Override
    public List<CompilationDto> findCompilations(boolean pinned, int from, int size) {
        return compilationRepository.findByPinnedIs(pinned, PageRequest.of(from, size))
                .stream()
                .map(CompilationMapper::toDto)
                .collect(Collectors.toList());
    }

    private Compilation getCompilation(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(
                () -> new IllegalArgumentException("Подборка с " + compId + " не найдена"));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new IllegalArgumentException("Событие с " + eventId + " не найдено"));
    }

}
