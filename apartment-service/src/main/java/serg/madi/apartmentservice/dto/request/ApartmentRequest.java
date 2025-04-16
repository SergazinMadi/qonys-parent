package serg.madi.apartmentservice.dto.request;

import lombok.Value;

import java.util.List;

/**
 * DTO for {@link serg.madi.apartmentservice.entity.Apartment}
 */
@Value
public class ApartmentRequest {
    String name;
    String description;
    String type;
    Integer capacity;
    String address;
    Double costPerNight;
    Double area;
    Long cityId;
    List<RoomRequest> rooms;
    Long[] amenityIds;
}