package serg.madi.core.dto.commands;

public record ProcessPayment(
    Long bookingId,
    Long apartmentId,
    Long apartmentBookingId,
    Long userId,
    Double amount
) {}
