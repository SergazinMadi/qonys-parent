package serg.madi.apartmentservice.dto.kafka_events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
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
