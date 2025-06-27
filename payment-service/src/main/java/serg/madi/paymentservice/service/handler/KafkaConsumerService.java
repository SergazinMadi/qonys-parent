package serg.madi.paymentservice.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import serg.madi.core.dto.commands.ProcessPayment;
import serg.madi.core.dto.events.PaymentFailed;
import serg.madi.core.dto.events.PaymentSucceeded;
import serg.madi.paymentservice.entity.Payment;
import serg.madi.paymentservice.entity.enums.PaymentMethod;
import serg.madi.paymentservice.entity.enums.PaymentStatus;
import serg.madi.paymentservice.repository.PaymentRepository;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "payment.commands")
public class KafkaConsumerService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate kafkaTemplate;

    @KafkaHandler
    public void handleProcessPayment(ProcessPayment processPayment){
        log.info("✅ Получено событие: " + processPayment);
        String status = Math.random() < 0.9 ? "SUCCESS" : "FAILED";

        Payment payment = Payment.builder()
                .amount(processPayment.amount())
                .apartmentId(processPayment.apartmentId())
                .userId(processPayment.userId())
                .bookingId(processPayment.bookingId())
                .method(PaymentMethod.CARD)
                .status(PaymentStatus.valueOf(status))
                .build();
        Payment savedPayment = paymentRepository.save(payment);

        if (savedPayment.getStatus().equals(PaymentStatus.SUCCESS)) {
            kafkaTemplate.send("payment.events", new PaymentSucceeded(
                    savedPayment.getId(),
                    savedPayment.getBookingId(),
                    savedPayment.getApartmentId(),
                    processPayment.apartmentBookingId(),
                    processPayment.userId(),
                    savedPayment.getAmount(),
                    LocalDate.now().atStartOfDay()
            ));
            log.info("Payment processed successfully: {}", savedPayment);

        } else {
            kafkaTemplate.send("payment.events", new PaymentFailed(
                    savedPayment.getId(),
                    savedPayment.getBookingId(),
                    savedPayment.getApartmentId(),
                    processPayment.apartmentBookingId(),
                    processPayment.userId(),
                    savedPayment.getAmount(),
                    "I dont now"
            ));
            log.info("Payment process failed: {}", savedPayment);
        }

    }

}
