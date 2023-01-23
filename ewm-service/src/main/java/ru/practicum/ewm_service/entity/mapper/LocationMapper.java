package ru.practicum.ewm_service.entity.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm_service.entity.Location;
import ru.practicum.ewm_service.entity.dto.location.LocationDto;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {

    public static Location fromDto(LocationDto dto) {
        Location location = new Location();
        location.setLat(dto.getLat());
        location.setLon(dto.getLon());

        return location;
    }

    public static LocationDto toDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

}
