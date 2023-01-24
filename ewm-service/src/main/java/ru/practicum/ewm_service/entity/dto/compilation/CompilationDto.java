package ru.practicum.ewm_service.entity.dto.compilation;

import lombok.*;
import ru.practicum.ewm_service.entity.shorts.EventShort;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {

    private List<EventShort> events;
    private Long id;
    private boolean pinned;
    private String title;


}
