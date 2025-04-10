package serg.madi.bookingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;


@Table(name = "bookings")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private Long apartmentId;

    @Column(nullable = false)
    @NotBlank
    private Long userId;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer peopleCount;
    private Float price;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
}
