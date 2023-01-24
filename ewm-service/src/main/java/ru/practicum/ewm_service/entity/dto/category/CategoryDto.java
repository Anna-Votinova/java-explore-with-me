package ru.practicum.ewm_service.entity.dto.category;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @NotBlank
    private String name;
}
