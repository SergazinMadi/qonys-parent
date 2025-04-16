package serg.madi.apartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.apartmentservice.entity.Room;
import serg.madi.apartmentservice.entity.enums.RoomType;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomTypeAndCountOfBeds(RoomType roomType, String countOfBeds);
}