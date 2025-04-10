package serg.madi.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "payments")
@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
}
