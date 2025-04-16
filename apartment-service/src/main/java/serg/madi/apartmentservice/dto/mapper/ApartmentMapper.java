package serg.madi.apartmentservice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import serg.madi.apartmentservice.dto.dto.ApartmentDto;
import serg.madi.apartmentservice.entity.Apartment;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApartmentMapper {
    Apartment toEntity(ApartmentDto apartmentDto);

    ApartmentDto toApartmentDto(Apartment apartment);

    List<ApartmentDto> toApartmentDto(List<Apartment> apartment);

    List<Apartment> toEntity(List<ApartmentDto> apartmentDto);
}