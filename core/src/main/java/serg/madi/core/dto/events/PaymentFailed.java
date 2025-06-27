package serg.madi.core.dto.events;

public record PaymentFailed(
        Long paymentId,
        Long bookingId,
        Long apartmentId,
        Long apartmentBookingId,
        Long userId,
        Double amount,
        String reason
) {}