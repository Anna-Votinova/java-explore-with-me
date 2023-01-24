package ru.practicum.ewm_service.entity.dto.location;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private float lat;
    private float lon;
}
