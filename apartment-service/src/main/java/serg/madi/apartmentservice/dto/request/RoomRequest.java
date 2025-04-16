package serg.madi.apartmentservice.dto.request;

import lombok.Value;

/**
 * DTO for {@link serg.madi.apartmentservice.entity.Room}
 */
@Value
public class RoomRequest {
    String roomType;
    String countOfBeds;
}