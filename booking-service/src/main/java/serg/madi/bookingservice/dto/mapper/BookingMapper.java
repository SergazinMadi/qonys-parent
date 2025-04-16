package serg.madi.bookingservice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import serg.madi.bookingservice.dto.dto.BookingDto;
import serg.madi.bookingservice.entity.Booking;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
    Booking toEntity(BookingDto bookingDto);

    BookingDto toBookingDto(Booking booking);
}