package serg.madi.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.reviewservice.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}