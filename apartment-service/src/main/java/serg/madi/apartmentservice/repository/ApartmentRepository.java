package serg.madi.apartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.apartmentservice.entity.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}