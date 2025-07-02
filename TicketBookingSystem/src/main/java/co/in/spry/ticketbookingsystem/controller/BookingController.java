package co.in.spry.ticketbookingsystem.controller;

import co.in.spry.ticketbookingsystem.dto.*;
import co.in.spry.ticketbookingsystem.entity.Event;
import co.in.spry.ticketbookingsystem.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }



    @PostMapping("/hold")
    public ResponseEntity<?> holdSeats(@RequestBody HoldSeatRequestDTO request) {
        Long holdId = bookingService.holdSeats(request.getEventId(), request.getNumberOfSeats(), request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(holdId);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmBooking(@RequestBody ConfirmBookingRequestDTO request) {
        Long bookingId = bookingService.confirmBooking(request.getHoldId());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingId);
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> cancelBooking(@RequestBody CancelBookingRequestDTO request) {
        bookingService.cancelBooking(request.getBookingId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableSeats(@RequestParam Long eventId) {
        int available = bookingService.getAvailableSeats(eventId);
        return ResponseEntity.ok(available);
    }

    @PostMapping("/cleanup")
    public ResponseEntity<?> cleanupExpired() {
        bookingService.releaseExpiredHolds();
        return ResponseEntity.ok("Expired holds cleaned up.");
    }

    //
    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = bookingService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        Event event = bookingService.getEventById(id);
        return ResponseEntity.ok(event);
    }
}
