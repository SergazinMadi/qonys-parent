package serg.madi.core.dto.events;

import java.time.LocalDateTime;

public record PaymentSucceeded(
        Long paymentId,
        Long bookingId,
        Long apartmentId,
        Long apartmentBookingId,
        Long userId,
        Double amount,
        LocalDateTime timestamp
) {}
