package serg.madi.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.bookingservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}