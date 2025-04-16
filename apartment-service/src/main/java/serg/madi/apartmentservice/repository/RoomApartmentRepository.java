package serg.madi.apartmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.madi.apartmentservice.entity.Apartment;
import serg.madi.apartmentservice.entity.Room;
import serg.madi.apartmentservice.entity.RoomApartment;

import java.util.List;

public interface RoomApartmentRepository extends JpaRepository<RoomApartment, Long> {
    List<RoomApartment> findAllByApartment(Apartment apartment);

    boolean existsByRoomAndApartmentNot(Room room, Apartment apartment);
}