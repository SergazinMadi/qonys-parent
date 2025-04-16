package serg.madi.bookingservice.dto.kafka_events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent extends KafkaEvent {
    private String eventType;
    private Long paymentId;
    private Long bookingId;
    private Long userId;
    private Long apartmentId;
    private Long apartmentBookId;
    private Double price;
    private String paymentMethod;
    private String status;
}
