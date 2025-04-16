package serg.madi.apartmentservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import serg.madi.apartmentservice.dto.dto.ApartmentDto;
import serg.madi.apartmentservice.dto.mapper.ApartmentMapper;
import serg.madi.apartmentservice.dto.request.ApartmentRequest;
import serg.madi.apartmentservice.entity.*;
import serg.madi.apartmentservice.entity.enums.RoomType;
import serg.madi.apartmentservice.repository.*;
import serg.madi.apartmentservice.service.ApartmentService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final CityRepository cityRepository;
    private final RoomRepository roomRepository;
    private final AmenityApartmentRepository amenityApartmentRepository;
    private final RoomApartmentRepository roomApartmentRepository;
    private final AmenityRepository amenityRepository;
    private final ApartmentMapper apartmentMapper;

    @Override
    public Page<ApartmentDto> getApartments(Pageable pageable) {
        Page<Apartment> apartments = apartmentRepository.findAll(pageable);
        return apartments.map(apartmentMapper::toApartmentDto);
    }

    @Override
    public ApartmentDto getApartment(Long id) {
        return apartmentMapper.toApartmentDto(apartmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Apartment not found")));
    }

    @Override
    public ApartmentDto createApartment(ApartmentRequest apartmentRequest) {
        Apartment apartment = new Apartment();
        requestToEntity(apartmentRequest, apartment);

        ApartmentDto savedApartment = apartmentMapper.toApartmentDto(apartmentRepository.save(apartment));
        saveAmenities(apartmentRequest, apartment);
        saveRooms(apartmentRequest, apartment);

        return savedApartment;
    }


    @Override
    public ApartmentDto updateApartment(Long id, ApartmentRequest apartmentRequest) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Apartment not found"));
        requestToEntity(apartmentRequest, apartment);

        ApartmentDto savedApartment = apartmentMapper.toApartmentDto(apartmentRepository.save(apartment));
        saveAmenities(apartmentRequest, apartment);
        saveRooms(apartmentRequest, apartment);

        return savedApartment;
    }

    @Override
    public void deleteApartment(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found"));

        List<RoomApartment> roomApartments = roomApartmentRepository.findAllByApartment(apartment);

        roomApartmentRepository.deleteAll(roomApartments);

        List<Room> unusedRooms = roomApartments.stream()
                .map(RoomApartment::getRoom)
                .distinct()
                .filter(room -> !roomApartmentRepository.existsByRoomAndApartmentNot(room, apartment))
                .toList();

        roomRepository.deleteAll(unusedRooms);

        apartmentRepository.delete(apartment);
    }

    private void requestToEntity(ApartmentRequest apartmentRequest, Apartment apartment) {
        apartment.setName(apartmentRequest.getName());
        apartment.setDescription(apartmentRequest.getDescription());
        apartment.setCapacity(apartmentRequest.getCapacity());
        apartment.setCity(cityRepository.getReferenceById(apartmentRequest.getCityId()));
        apartment.setType(apartmentRequest.getType());
        apartment.setAddress(apartmentRequest.getAddress());
        apartment.setCostPerNight(apartmentRequest.getCostPerNight());
        apartment.setArea(apartmentRequest.getArea());
    }

    private void saveAmenities(ApartmentRequest apartmentRequest, Apartment apartment) {
        List<Amenity> amenities = amenityRepository.findAllById(List.of(apartmentRequest.getAmenityIds()));

        List<AmenityApartment> amenityApartments = amenities.stream()
                .map(amenity -> {
                    AmenityApartment aa = new AmenityApartment();
                    aa.setApartment(apartment);
                    aa.setAmenity(amenity);
                    return aa;
                })
                .toList();

        amenityApartmentRepository.saveAll(amenityApartments);
    }

    private void saveRooms(ApartmentRequest apartmentRequest, Apartment apartment) {
        List<RoomApartment> roomApartments = apartmentRequest.getRooms().stream()
                .map(room -> {
                    Room roomEntity = roomRepository.findByRoomTypeAndCountOfBeds(
                            RoomType.valueOf(room.getRoomType()),
                            room.getCountOfBeds()
                    ).orElseGet(() -> roomRepository.save(
                            Room.builder()
                                    .roomType(RoomType.valueOf(room.getRoomType()))
                                    .countOfBeds(room.getCountOfBeds())
                                    .build()
                    ));

                    RoomApartment roomApartment = new RoomApartment();
                    roomApartment.setApartment(apartment);
                    roomApartment.setRoom(roomEntity);
                    return roomApartment;
                })
                .toList();

        roomApartmentRepository.saveAll(roomApartments);
    }

}
