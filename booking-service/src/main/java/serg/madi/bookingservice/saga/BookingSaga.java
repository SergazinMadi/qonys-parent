package serg.madi.bookingservice.saga;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import serg.madi.bookingservice.entity.Booking;
import serg.madi.bookingservice.entity.enums.BookingStatus;
import serg.madi.bookingservice.repository.BookingRepository;
import serg.madi.core.dto.commands.*;
import serg.madi.core.dto.events.*;

@Component
@KafkaListener(topics = {
        "booking.events",
        "apartment.events",
        "payment.events",
})
@RequiredArgsConstructor
public class BookingSaga {

    private static final Logger log = LoggerFactory.getLogger(BookingSaga.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String apartmentCommandsTopicName = "apartment.commands";
    private final String paymentCommandsTopicName = "payment.commands";
    private final String userCommandsTopicName = "user.commands";
    private final BookingRepository bookingRepository;

    @KafkaHandler
    public void handleBookingCreated(@Payload BookingCreated bookingCreated) {
        log.info("Received BookingCreated event: {}", bookingCreated);
        ReserveApartment reserveApartment = new ReserveApartment(
                bookingCreated.bookingId(),
                bookingCreated.apartmentId(),
                bookingCreated.userId(),
                bookingCreated.price(),
                bookingCreated.startDate(),
                bookingCreated.endDate()
        );
        kafkaTemplate.send(apartmentCommandsTopicName, reserveApartment);
        log.info("Sent ReserveApartment command: {}", reserveApartment);
    }

    @KafkaHandler
    public void handleApartmentReserved(@Payload ApartmentReserved apartmentReserved) {
        log.info("Received ApartmentReserved event: {}", apartmentReserved);
        ProcessPayment processPayment = new ProcessPayment(
                apartmentReserved.bookingId(),
                apartmentReserved.apartmentId(),
                apartmentReserved.apartmentBookingId(),
                apartmentReserved.userId(),
                apartmentReserved.price()
        );
        kafkaTemplate.send(paymentCommandsTopicName, processPayment);
        log.info("Sent ProcessPayment command: {}", processPayment);
    }

    @KafkaHandler
    public void handlePaymentSucceeded(@Payload PaymentSucceeded paymentSucceeded) {
        log.info("Received PaymentSucceeded event: {}", paymentSucceeded);
        Booking booking = bookingRepository.findById(paymentSucceeded.bookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found: " + paymentSucceeded.bookingId()));
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        log.info("Booking confirmed: {}", booking.getId());

        kafkaTemplate.send(userCommandsTopicName, booking.getUserId());
        kafkaTemplate.send(apartmentCommandsTopicName, new ConfirmApartmentBooking(
                paymentSucceeded.apartmentId(),
                paymentSucceeded.apartmentBookingId()
        ));
        log.info("Sent ConfirmApartmentBooking command and notified user: {}", booking.getUserId());
    }

    @KafkaHandler
    public void handleApartmentReservationFailed(@Payload ApartmentReservationFailed event) {
        log.info("Received ApartmentReservationFailed event: {}", event);
        Booking booking = bookingRepository.findById(event.bookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found: " + event.bookingId()));
        booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
        log.info("Booking rejected due to apartment reservation failure: {}", booking.getId());
    }

    @KafkaHandler
    public void handlePaymentFailed(@Payload PaymentFailed event) {
        log.info("Received PaymentFailed event: {}", event);
        Booking booking = bookingRepository.findById(event.bookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found: " + event.bookingId()));
        booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
        log.info("Booking rejected due to payment failure: {}", booking.getId());

        kafkaTemplate.send(apartmentCommandsTopicName, new ReleaseApartment(
                event.apartmentId(),
                event.apartmentBookingId()
        ));
        log.info("Sent ReleaseApartment command for bookingId={}", event.bookingId());
    }
}
