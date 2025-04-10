package serg.madi.apartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.apartmentservice.entity.AmenityApartment;

public interface AmenityApartmentRepository extends JpaRepository<AmenityApartment, Long> {
}