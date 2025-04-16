package serg.madi.apartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.apartmentservice.entity.Apartment;
import serg.madi.apartmentservice.entity.ApartmentBooks;

import java.util.List;

public interface ApartmentBooksRepository extends JpaRepository<ApartmentBooks, Long> {
    List<ApartmentBooks> findByApartment(Apartment apartment);
}