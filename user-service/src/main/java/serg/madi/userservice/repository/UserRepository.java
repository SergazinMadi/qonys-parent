package serg.madi.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import serg.madi.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
