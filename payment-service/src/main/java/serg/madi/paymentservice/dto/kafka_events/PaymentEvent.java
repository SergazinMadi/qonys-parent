package serg.madi.paymentservice.dto.kafka_events;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
