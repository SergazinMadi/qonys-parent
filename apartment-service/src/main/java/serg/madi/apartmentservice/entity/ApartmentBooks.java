package serg.madi.apartmentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import serg.madi.apartmentservice.entity.enums.BookingStatus;

import java.time.LocalDate;

@Entity
@Table(name = "apartment_books")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ApartmentBooks {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    private LocalDate checkIn;
    private LocalDate checkOut;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
