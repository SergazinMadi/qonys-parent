package serg.madi.core.dto.events;

import java.time.LocalDate;

public record BookingCreated (
    Long bookingId,
    Long userId,
    Long apartmentId,
    LocalDate startDate,
    LocalDate endDate,
    Double price
){}