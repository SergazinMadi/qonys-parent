package serg.madi.apartmentservice.dto.dto;

import lombok.Value;

/**
 * DTO for {@link serg.madi.apartmentservice.entity.City}
 */
@Value
public class CityDto {
    Long id;
    String name;
}