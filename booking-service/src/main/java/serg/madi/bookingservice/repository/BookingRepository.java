package serg.madi.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.bookingservice.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}