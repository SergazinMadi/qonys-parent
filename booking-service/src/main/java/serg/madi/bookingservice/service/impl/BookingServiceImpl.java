package serg.madi.bookingservice.service.impl;

    import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import serg.madi.bookingservice.dto.dto.BookingDto;
import serg.madi.bookingservice.dto.request.BookingRequest;
import serg.madi.bookingservice.entity.Booking;
import serg.madi.bookingservice.dto.mapper.BookingMapper;
import serg.madi.bookingservice.entity.enums.BookingStatus;
import serg.madi.bookingservice.repository.BookingRepository;
import serg.madi.bookingservice.service.BookingService;
import serg.madi.core.dto.events.BookingCreated;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public BookingDto getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking with id " + bookingId + " not found"));
        return bookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookings(Pageable pageable) {
        List<Booking> bookings = bookingRepository.findAll(pageable).getContent();
        return bookings.stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllBookingsOfUser(Long userId) {
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);
        return bookings.stream().map(bookingMapper::toBookingDto).collect(Collectors.toList());
    }

    @Override
    public BookingDto createBooking(BookingRequest bookingRequest){
        Booking booking = new Booking();
        booking.setStatus(BookingStatus.CREATED);
        fromRequest(bookingRequest, booking);
        Booking savedBooking = bookingRepository.save(booking);
        log.info("booing created: {}", savedBooking);
        kafkaTemplate.send("booking.events", toBookingCreated(savedBooking));
        return bookingMapper.toBookingDto(savedBooking);
    }

    @Override
    public BookingDto updateBooking(Long bookingId, BookingRequest bookingRequest) {
        Booking booking = bookingRepository.getReferenceById(bookingId);
        fromRequest(bookingRequest, booking);
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toBookingDto(savedBooking);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    private void fromRequest(BookingRequest bookingRequest, Booking booking) {
        booking.setUserId(bookingRequest.getUserId());
        booking.setApartmentId(bookingRequest.getApartmentId());
        booking.setPrice(bookingRequest.getPrice());
        booking.setCheckIn(bookingRequest.getCheckIn());
        booking.setCheckOut(bookingRequest.getCheckOut());
        booking.setPeopleCount(bookingRequest.getPeopleCount());
    }

    private BookingCreated toBookingCreated(Booking booking) {
        return new BookingCreated(
                booking.getId(),
                booking.getUserId(),
                booking.getApartmentId(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getPrice());
    }
}
