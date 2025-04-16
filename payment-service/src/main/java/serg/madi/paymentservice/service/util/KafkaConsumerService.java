package serg.madi.paymentservice.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import serg.madi.paymentservice.dto.kafka_events.ApartmentEvent;
import serg.madi.paymentservice.dto.kafka_events.BookingKafkaEvent;
import serg.madi.paymentservice.dto.kafka_events.PaymentEvent;
import serg.madi.paymentservice.entity.Payment;
import serg.madi.paymentservice.entity.enums.PaymentMethod;
import serg.madi.paymentservice.entity.enums.PaymentStatus;
import serg.madi.paymentservice.repository.PaymentRepository;

@Component
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper;
    private final KafkaProducerService kafkaProducerService;

    @KafkaListener(topics = "payment.requested", groupId = "payment")
    public void handlePaymentRequest(String message) throws JsonProcessingException {
        System.out.println("✅ Получено событие: " + message);
        ApartmentEvent apartmentEvent = objectMapper.readValue(message, ApartmentEvent.class);

        String status = Math.random() < 0.9 ? "SUCCESS" : "FAILED";

        Payment payment = Payment.builder()
                .amount(apartmentEvent.getPrice())
                .apartmentId(apartmentEvent.getApartmentId())
                .userId(apartmentEvent.getUserId())
                .bookingId(apartmentEvent.getBookingId())
                .method(PaymentMethod.CARD)
                .status(PaymentStatus.valueOf(status))
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        PaymentEvent paymentEvent = toKafkaEvent(savedPayment, apartmentEvent.getApartmentBookId());
        kafkaProducerService.sendEvent("payment.topic", paymentEvent);

    }

    private PaymentEvent toKafkaEvent(Payment payment, Long apartmentBookId) {
        String eventType;
        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            eventType = "PAYMENT_SUCCESS";
        } else {
            eventType = "PAYMENT_FAILED";
        }
        return PaymentEvent.builder()
                .paymentMethod(payment.getMethod().name())
                .paymentId(payment.getId())
                .price(payment.getAmount())
                .userId(payment.getUserId())
                .apartmentId(payment.getApartmentId())
                .bookingId(payment.getBookingId())
                .status(payment.getStatus().name())
                .apartmentBookId(apartmentBookId)
                .eventType(eventType)
                .build();
    }
}
