package serg.madi.apartmentservice.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import serg.madi.apartmentservice.dto.kafka_events.ApartmentEvent;
import serg.madi.apartmentservice.dto.kafka_events.BookingEvent;
import serg.madi.apartmentservice.dto.kafka_events.PaymentEvent;
import serg.madi.apartmentservice.entity.Apartment;
import serg.madi.apartmentservice.entity.ApartmentBooks;
import serg.madi.apartmentservice.entity.enums.BookingStatus;
import serg.madi.apartmentservice.repository.ApartmentBooksRepository;
import serg.madi.apartmentservice.repository.ApartmentRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentBooksRepository apartmentBooksRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "booking.requested", groupId = "apartment")
    @Transactional
    public void handleBookingRequest(String message) throws JsonProcessingException {
        System.out.println("✅ Получено событие: " + message);

        BookingEvent bookingEvent = objectMapper.readValue(message, BookingEvent.class);
        Apartment apartment = apartmentRepository.findById(bookingEvent.getApartmentId())
                .orElseThrow(() -> {
                    log.info("Apartment not found");
                    return new EntityNotFoundException("Apartment not found");
                });


        LocalDate requestedCheckIn = bookingEvent.getCheckIn();
        LocalDate requestedCheckOut = bookingEvent.getCheckOut();

        apartmentBooksRepository.findByApartment(apartment)
                .forEach(apartmentBooks -> {
                    if (apartmentBooks.getCheckIn().isBefore(requestedCheckOut) &&
                            apartmentBooks.getCheckOut().isAfter(requestedCheckIn)) {
                        log.info("Apartment is already booked");
                        throw new IllegalStateException("Apartment is already booked");
                    }
                });

        ApartmentBooks apartmentBooks = new ApartmentBooks();
        apartmentBooks.setApartment(apartment);
        apartmentBooks.setCheckIn(requestedCheckIn);
        apartmentBooks.setCheckOut(requestedCheckOut);
        apartmentBooks.setBookingStatus(BookingStatus.TEMPORARY);
        ApartmentBooks savedApartmentBooks = apartmentBooksRepository.save(apartmentBooks);

        ApartmentEvent apartmentEvent = ApartmentEvent.builder()
                .apartmentId(apartment.getId())
                .price(bookingEvent.getPrice())
                .bookingId(bookingEvent.getBookingId())
                .userId(bookingEvent.getUserId())
                .apartmentBookId(savedApartmentBooks.getId())
                .build();

        System.out.println("Apartment booked temporary" + bookingEvent.getApartmentId());

        kafkaProducerService.sendEvent("payment.requested", apartmentEvent);
    }

    @KafkaListener(topics = "payment.topic", groupId = "apartment")
    public void handlePaymentEvent(String message) throws JsonProcessingException {
        System.out.println("✅ Получено событие: " + message);
        PaymentEvent paymentEvent = new ObjectMapper().readValue(message, PaymentEvent.class);
        ApartmentBooks apartmentBooks = apartmentBooksRepository.findById(paymentEvent.getApartmentBookId())
                .orElseThrow(() -> new RuntimeException("Бронирование не найдено"));
        switch (paymentEvent.getEventType()) {
            case "PAYMENT_SUCCESS" -> {
                log.info("✅ Успешный платеж: " + paymentEvent);
                apartmentBooks.setBookingStatus(BookingStatus.CONFIRMED);
                apartmentBooksRepository.save(apartmentBooks);
            }
            case "PAYMENT_FAILED" -> {
                log.info("❌ Ошибка платежа: " + paymentEvent);
                apartmentBooksRepository.delete(apartmentBooks);
            }
            default -> System.out.println("❗ Неизвестный тип события: " + paymentEvent.getEventType());
        }
    }

}