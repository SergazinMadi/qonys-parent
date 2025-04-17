package serg.madi.bookingservice.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serg.madi.bookingservice.dto.dto.BookingDto;
import serg.madi.bookingservice.dto.request.BookingRequest;
import serg.madi.bookingservice.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking (@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings (@PageableDefault Pageable pageable) {
        List<BookingDto> bookings = bookingService.getAllBookings(pageable);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user")
    public ResponseEntity<List<BookingDto>> getAllBookings () {
        List<BookingDto> bookings = bookingService.getAllBookingsOfUser(0L);
        return ResponseEntity.ok(bookings);
    }


    @PostMapping
    public ResponseEntity<BookingDto> createBooking (@RequestBody BookingRequest bookingRequest) throws JsonProcessingException {
        return ResponseEntity.ok(bookingService.createBooking(bookingRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingDto> deleteBooking (@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
