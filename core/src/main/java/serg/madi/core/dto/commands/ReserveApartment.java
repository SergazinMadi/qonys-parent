package serg.madi.core.dto.commands;

import java.time.LocalDate;

public record ReserveApartment(
    Long bookingId,
    Long apartmentId,
    Long userId,
    Double price,
    LocalDate startDate,
    LocalDate endDate
) {}
