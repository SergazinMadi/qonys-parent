package serg.madi.bookingservice.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import serg.madi.bookingservice.dto.kafka_events.PaymentEvent;
import serg.madi.bookingservice.entity.Booking;
import serg.madi.bookingservice.entity.enums.BookingStatus;
import serg.madi.bookingservice.repository.BookingRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final BookingRepository bookingRepository;

    @KafkaListener(topics = "payment.topic", groupId = "booking")
    public void handlePaymentEvent(String message) throws JsonProcessingException {
        System.out.println("✅ Получено событие: " + message);
        PaymentEvent paymentEvent = new ObjectMapper().readValue(message, PaymentEvent.class);
        Booking booking = bookingRepository.findById(paymentEvent.getBookingId()).orElseThrow(() -> new RuntimeException("Бронирование не найдено"));
        switch (paymentEvent.getEventType()) {
            case "PAYMENT_SUCCESS" -> {
                log.info("✅ Успешный платеж: " + paymentEvent);
                booking.setStatus(BookingStatus.CONFIRMED);
            }
            case "PAYMENT_FAILED" -> {
                log.info("❌ Ошибка платежа: " + paymentEvent);
                booking.setStatus(BookingStatus.FAILED);
            }
            default -> System.out.println("❗ Неизвестный тип события: " + paymentEvent.getEventType());
        }

    }
}
