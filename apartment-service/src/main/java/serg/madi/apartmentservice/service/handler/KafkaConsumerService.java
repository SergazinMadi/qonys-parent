package serg.madi.apartmentservice.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import serg.madi.apartmentservice.entity.ApartmentBooking;
import serg.madi.apartmentservice.entity.enums.BookingStatus;
import serg.madi.apartmentservice.repository.ApartmentBooksRepository;
import serg.madi.apartmentservice.repository.ApartmentRepository;
import serg.madi.core.dto.commands.ConfirmApartmentBooking;
import serg.madi.core.dto.commands.ReleaseApartment;
import serg.madi.core.dto.commands.ReserveApartment;
import serg.madi.core.dto.events.ApartmentReservationFailed;
import serg.madi.core.dto.events.ApartmentReserved;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "apartment.commands")
public class KafkaConsumerService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentBooksRepository apartmentBooksRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    @KafkaHandler
    public void handleReserveApartment(ReserveApartment reserveApartment) {
        log.info("✅ Получено событие: {}", reserveApartment);

        apartmentRepository.findById(reserveApartment.apartmentId()).ifPresentOrElse(
                apartment -> {
                    if (apartmentBooksRepository.existsByApartmentAndCheckInBeforeAndCheckOutAfter(
                            apartment, reserveApartment.endDate(), reserveApartment.startDate())) {
                        ApartmentReservationFailed failedEvent = new ApartmentReservationFailed(
                                reserveApartment.bookingId(),
                                reserveApartment.apartmentId(),
                                "Apartment is already booked for the selected dates"
                        );
                        kafkaTemplate.send("apartment.events", failedEvent);
                        log.warn("❌ Apartment is already booked: {}", failedEvent);
                        return;
                    }

                    ApartmentBooking apartmentBooking = new ApartmentBooking();
                    apartmentBooking.setApartment(apartment);
                    apartmentBooking.setCheckIn(reserveApartment.startDate());
                    apartmentBooking.setCheckOut(reserveApartment.endDate());
                    apartmentBooking.setBookingStatus(BookingStatus.CREATED);
                    ApartmentBooking savedBooking = apartmentBooksRepository.save(apartmentBooking);

                    ApartmentReserved apartmentReserved = new ApartmentReserved(
                            reserveApartment.bookingId(),
                            reserveApartment.apartmentId(),
                            reserveApartment.userId(),
                            savedBooking.getId(),
                            reserveApartment.price(),
                            reserveApartment.startDate(),
                            reserveApartment.endDate()
                    );

                    kafkaTemplate.send("apartment.events", apartmentReserved);
                    log.info("Apartment booking created successfully {}", apartmentReserved);
                }, () -> {
                    ApartmentReservationFailed failedEvent = new ApartmentReservationFailed(
                            reserveApartment.bookingId(),
                            reserveApartment.apartmentId(),
                            "Apartment not found"
                    );
                    kafkaTemplate.send("apartment.events", failedEvent);
                    log.warn("Apartment not found, failed event sent: {}", failedEvent);
                });
    }

    @Transactional
    @KafkaHandler
    public void handleConfirmApartmentBooking(ConfirmApartmentBooking confirmApartmentBooking) {
        log.info("kafka-message - confirmApartmentBooking");
        ApartmentBooking apartmentBooking = apartmentBooksRepository.findById(confirmApartmentBooking.apartmentBookingId()).orElseThrow();
        apartmentBooking.setBookingStatus(BookingStatus.CONFIRMED);
        apartmentBooksRepository.save(apartmentBooking);
        log.info("Apartment booking confirmed successfully {}", confirmApartmentBooking);
    }

    @Transactional
    @KafkaHandler
    public void handleReleaseApartment(ReleaseApartment releaseApartment) {
        log.info("kafka-message - releaseApartment");
        ApartmentBooking apartmentBooking = apartmentBooksRepository.findById(releaseApartment.apartmentBookingId()).orElseThrow();
        apartmentBooking.setBookingStatus(BookingStatus.REJECTED);
        apartmentBooksRepository.save(apartmentBooking);
        log.info("Apartment booking rejected successfully {}", releaseApartment);
    }
}
