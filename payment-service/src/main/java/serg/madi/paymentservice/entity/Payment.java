package serg.madi.paymentservice.entity;


import jakarta.persistence.*;
import lombok.*;
import serg.madi.paymentservice.entity.enums.PaymentMethod;
import serg.madi.paymentservice.entity.enums.PaymentStatus;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookingId;
    private Long apartmentId;
    private Long userId;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Например: "SUCCESS", "FAILED", "PENDING"

    private LocalDate createdAt;

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDate.now();
    }
}
