package co.in.spry.ticketbookingsystem.service;

import co.in.spry.ticketbookingsystem.entity.Booking;
import co.in.spry.ticketbookingsystem.entity.Event;
import co.in.spry.ticketbookingsystem.entity.SeatHold;
import co.in.spry.ticketbookingsystem.exceptions.BookingException;
import co.in.spry.ticketbookingsystem.exceptions.ResourceNotFoundException;
import co.in.spry.ticketbookingsystem.repository.BookingRepository;
import co.in.spry.ticketbookingsystem.repository.EventRepository;
import co.in.spry.ticketbookingsystem.repository.SeatHoldRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final EventRepository eventRepo;
    private final SeatHoldRepository seatHoldRepo;
    private final BookingRepository bookingRepo;

    public BookingServiceImpl(EventRepository eventRepo,
                              SeatHoldRepository seatHoldRepo,
                              BookingRepository bookingRepo) {
        this.eventRepo = eventRepo;
        this.seatHoldRepo = seatHoldRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    @Transactional
    public Long holdSeats(Long eventId, int seats, String userId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        int heldSeats = getHeldSeats(eventId);
        int bookedSeats = getBookedSeats(eventId);

        if ((heldSeats + bookedSeats + seats) > event.getTotalSeats()) {
            throw new BookingException("Not enough seats available.");
        }

        SeatHold hold = new SeatHold();
        hold.setEventId(eventId);
        hold.setUserId(userId);
        hold.setNumberOfSeats(seats);
        hold.setCreatedAt(LocalDateTime.now());
        hold.setConfirmed(false);

        return seatHoldRepo.save(hold).getId();
    }

    @Override
    @Transactional
    public Long confirmBooking(Long holdId) {
        SeatHold hold = seatHoldRepo.findById(holdId)
                .orElseThrow(() -> new ResourceNotFoundException("Hold not found"));

        if (hold.isExpired()) {
            throw new BookingException("Hold has expired.");
        }

        hold.setConfirmed(true);
        seatHoldRepo.save(hold);

        Booking booking = new Booking();
        booking.setEventId(hold.getEventId());
        booking.setUserId(hold.getUserId());
        booking.setNumberOfSeats(hold.getNumberOfSeats());
        booking.setBookedAt(LocalDateTime.now());
        booking.setCanceled(false);

        return bookingRepo.save(booking).getId();
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setCanceled(true);
        bookingRepo.save(booking);
    }

    @Override
    @Transactional
    public void releaseExpiredHolds() {
        List<SeatHold> expiredHolds = seatHoldRepo
                .findByConfirmedFalseAndCreatedAtBefore(LocalDateTime.now().minusMinutes(5));
        seatHoldRepo.deleteAll(expiredHolds);
    }

    @Override
    public int getAvailableSeats(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return event.getTotalSeats() - (getHeldSeats(eventId) + getBookedSeats(eventId));
    }

    private int getHeldSeats(Long eventId) {
        List<SeatHold> holds = seatHoldRepo.findByEventIdAndConfirmedFalse(eventId);
        return holds.stream()
                .filter(hold -> !hold.isExpired())
                .mapToInt(SeatHold::getNumberOfSeats)
                .sum();
    }

    private int getBookedSeats(Long eventId) {
        List<Booking> bookings = bookingRepo.findByEventIdAndCanceledFalse(eventId);
        return bookings.stream()
                .mapToInt(Booking::getNumberOfSeats)
                .sum();
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepo.save(event);
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

}
