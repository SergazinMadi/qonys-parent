package serg.madi.bookingservice.dto.kafka_events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import serg.madi.bookingservice.entity.Booking;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingKafkaEvent extends KafkaEvent {
    private Long bookingId;
    private Long userId;
    private Long apartmentId;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate checkIn;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate checkOut;

    public static BookingKafkaEvent fromBookingDto(Booking booking) {
        BookingKafkaEvent event = new BookingKafkaEvent();
        event.setBookingId(booking.getId());
        event.setUserId(booking.getUserId());
        event.setApartmentId(booking.getApartmentId());
        event.setCheckIn(booking.getCheckIn());
        event.setCheckOut(booking.getCheckOut());
        return event;
    }
}
