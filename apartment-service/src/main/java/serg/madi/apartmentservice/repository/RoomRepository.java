package serg.madi.apartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.apartmentservice.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}