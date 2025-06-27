package serg.madi.core.dto.events;

public record ApartmentReservationFailed(
    Long bookingId,
    Long apartmentId,
    String reason
) {}