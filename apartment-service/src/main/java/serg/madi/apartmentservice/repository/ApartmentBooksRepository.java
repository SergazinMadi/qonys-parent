package serg.madi.apartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.apartmentservice.entity.Apartment;
import serg.madi.apartmentservice.entity.ApartmentBooking;

import java.time.LocalDate;
import java.util.List;

public interface ApartmentBooksRepository extends JpaRepository<ApartmentBooking, Long> {
    List<ApartmentBooking> findByApartment(Apartment apartment);
    boolean existsByApartmentAndCheckInBeforeAndCheckOutAfter(
            Apartment apartment,
            LocalDate checkIn,
            LocalDate checkOut);
}