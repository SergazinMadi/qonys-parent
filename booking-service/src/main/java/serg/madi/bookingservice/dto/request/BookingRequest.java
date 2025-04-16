package serg.madi.bookingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class BookingRequest {
    Long apartmentId;
    Long userId;
    LocalDate checkIn;
    LocalDate checkOut;
    Integer peopleCount;
    Float price;
}