package serg.madi.core.dto.events;

public record BookingRejected(
    String bookingId,
    String reason
) {}