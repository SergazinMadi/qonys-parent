package serg.madi.core.dto.commands;

public record ReleaseApartment(
    Long apartmentId,
    Long apartmentBookingId
) {}