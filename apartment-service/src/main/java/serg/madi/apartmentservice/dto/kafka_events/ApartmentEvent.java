package serg.madi.apartmentservice.dto.kafka_events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class ApartmentEvent extends KafkaEvent {
    private Long bookingId;
    private Long userId;
    private Long apartmentId;
    private Long apartmentBookId;
    private Double price;
}
