package serg.madi.favoriteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.favoriteservice.entity.FavoriteApartment;

public interface FavoriteApartmentRepository extends JpaRepository<FavoriteApartment, Long> {
}