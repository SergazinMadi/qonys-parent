package serg.madi.core.dto.commands;

public record RefundPayment(
    String bookingId,
    Double amount
) {}