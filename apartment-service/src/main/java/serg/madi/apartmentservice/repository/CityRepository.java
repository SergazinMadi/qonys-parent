package serg.madi.apartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.apartmentservice.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
}