package serg.madi.core.dto.events;

import java.time.LocalDate;

public record ApartmentReserved(
    Long bookingId,
    Long apartmentId,
    Long userId,
    Long apartmentBookingId,
    Double price,
    LocalDate startDate,
    LocalDate endDate
) {}
