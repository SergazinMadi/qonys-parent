package serg.madi.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.paymentservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}