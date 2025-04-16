package serg.madi.bookingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import serg.madi.bookingservice.dto.dto.BookingDto;
import serg.madi.bookingservice.dto.request.BookingRequest;

import java.util.List;

public interface BookingService {
    BookingDto getBookingById(Long bookingId);
    List<BookingDto> getAllBookings(Pageable pageable);
    List<BookingDto> getAllBookingsOfUser(Long userId);
    BookingDto createBooking(BookingRequest bookingRequest) throws JsonProcessingException;
    BookingDto updateBooking(Long bookingId, BookingRequest bookingRequest);
    void deleteBooking(Long bookingId);


}
