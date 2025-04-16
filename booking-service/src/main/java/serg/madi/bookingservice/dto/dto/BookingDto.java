package serg.madi.bookingservice.dto.dto;

import lombok.Value;
import serg.madi.bookingservice.entity.Booking;

import java.time.LocalDate;

/**
 * DTO for {@link Booking}
 */
@Value
public class BookingDto {
    Long id;
    Long apartmentId;
    Long userId;
    LocalDate checkIn;
    LocalDate checkOut;
    Integer peopleCount;
    Float price;
}