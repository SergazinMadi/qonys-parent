package serg.madi.apartmentservice.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import serg.madi.apartmentservice.service.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import serg.madi.apartmentservice.dto.dto.ApartmentDto;
import serg.madi.apartmentservice.dto.request.ApartmentRequest;


@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
@Tag(name = "Apartment API", description = "CRUD operations for Apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    @Operation(summary = "Get all apartments with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))
    })
    @GetMapping
    public ResponseEntity<Page<ApartmentDto>> getApartments (@PageableDefault Pageable pageable) {
        Page<ApartmentDto> apartments = apartmentService.getApartments(pageable);
        return ResponseEntity.ok(apartments);
    }

    @Operation(summary = "Get apartment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved apartment",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Apartment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApartmentDto> getApartment(
            @Parameter(description = "ID of the apartment to retrieve") @PathVariable Long id) {
        ApartmentDto dto = apartmentService.getApartment(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Create a new apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Apartment created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<ApartmentDto> createApartment(
            @RequestBody ApartmentRequest apartmentRequest) {
        ApartmentDto created = apartmentService.createApartment(apartmentRequest);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Update existing apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartment updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Apartment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDto> updateApartment(
            @Parameter(description = "ID of the apartment to update") @PathVariable Long id,
            @RequestBody ApartmentRequest apartmentRequest) {
        ApartmentDto updated = apartmentService.updateApartment(id, apartmentRequest);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete apartment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Apartment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Apartment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(
            @Parameter(description = "ID of the apartment to delete") @PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }
}
