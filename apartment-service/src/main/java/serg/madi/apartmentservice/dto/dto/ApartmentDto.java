package serg.madi.apartmentservice.dto.dto;

import lombok.Value;
import serg.madi.apartmentservice.dto.request.RoomRequest;
import serg.madi.apartmentservice.entity.Apartment;

import java.util.List;

/**
 * DTO for {@link Apartment}
 */
@Value
public class ApartmentDto {
    Long id;
    String name;
    String description;
    String type;
    Integer capacity;
    String address;
    Double costPerNight;
    Double area;
    CityDto city;
    List<RoomDto> rooms;
    List<AmenityDto> amenities;
}