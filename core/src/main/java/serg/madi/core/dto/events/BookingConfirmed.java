package serg.madi.core.dto.events;

import java.time.LocalDate;

public record BookingConfirmed(
    String bookingId,
    LocalDate startDate,
    LocalDate endDate
) {}