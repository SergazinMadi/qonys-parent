package serg.madi.apartmentservice.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serg.madi.apartmentservice.dto.dto.ApartmentDto;
import serg.madi.apartmentservice.dto.request.ApartmentRequest;
import serg.madi.apartmentservice.service.ApartmentService;

@RestController
@RequestMapping("/apartments")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    @GetMapping
    public ResponseEntity<Page<ApartmentDto>> getApartments (@PageableDefault Pageable pageable) {
        Page<ApartmentDto> apartments = apartmentService.getApartments(pageable);
        return ResponseEntity.ok(apartments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentDto> getApartment(@PathVariable Long id) {
        ApartmentDto dto = apartmentService.getApartment(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ApartmentDto> createApartment(
            @RequestBody ApartmentRequest apartmentRequest) {
        ApartmentDto created = apartmentService.createApartment(apartmentRequest);
        return ResponseEntity.status(201).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDto> updateApartment(
            @PathVariable Long id,
            @RequestBody ApartmentRequest apartmentRequest) {
        ApartmentDto updated = apartmentService.updateApartment(id, apartmentRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(
            @PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }
}
