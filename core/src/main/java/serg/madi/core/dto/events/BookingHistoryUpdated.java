package serg.madi.core.dto.events;

import java.time.LocalDate;

public record BookingHistoryUpdated(
    Long userId,
    String bookingId,
    String status,
    LocalDate startDate,
    LocalDate endDate
) {}