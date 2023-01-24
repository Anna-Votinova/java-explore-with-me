package ru.practicum.ewm_service.entity.dto.compilation;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    private List<Long> events;

    @Builder.Default
    private boolean pinned = false;
    @NotBlank
    private String title;
}
