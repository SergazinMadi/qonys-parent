package serg.madi.apartmentservice.dto.dto;

import lombok.Value;
import serg.madi.apartmentservice.entity.Room;

/**
 * DTO for {@link Room}
 */
@Value
public class RoomDto {
    Long id;
    String roomType;
    String countOfBeds;
}