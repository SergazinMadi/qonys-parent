package serg.madi.paymentservice.dto.kafka_events;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApartmentEvent extends KafkaEvent {
    private Long bookingId;
    private Long userId;
    private Long apartmentId;
    private Long apartmentBookId;
    private Double price;
}
