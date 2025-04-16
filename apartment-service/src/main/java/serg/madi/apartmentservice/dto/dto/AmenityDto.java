package serg.madi.apartmentservice.dto.dto;

import lombok.Value;

/**
 * DTO for {@link serg.madi.apartmentservice.entity.Amenity}
 */
@Value
public class AmenityDto {
    Long id;
    String name;
}