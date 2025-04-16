package serg.madi.apartmentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import serg.madi.apartmentservice.dto.dto.ApartmentDto;
import serg.madi.apartmentservice.dto.request.ApartmentRequest;

public interface ApartmentService {
    Page<ApartmentDto> getApartments(Pageable pageable);
    ApartmentDto getApartment(Long id);
    ApartmentDto createApartment(ApartmentRequest apartmentRequest);
    ApartmentDto updateApartment(Long id, ApartmentRequest apartmentRequest);
    void deleteApartment(Long id);
}
