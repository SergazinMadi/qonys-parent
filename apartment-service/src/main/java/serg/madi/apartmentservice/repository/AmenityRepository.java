package serg.madi.apartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.apartmentservice.entity.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}