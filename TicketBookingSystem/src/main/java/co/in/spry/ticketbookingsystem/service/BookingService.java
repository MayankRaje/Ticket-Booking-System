package co.in.spry.ticketbookingsystem.service;
import co.in.spry.ticketbookingsystem.entity.*;


public interface BookingService {
    Long holdSeats(Long eventId, int seats, String userId);
    Long confirmBooking(Long holdId);
    void cancelBooking(Long bookingId);
    void releaseExpiredHolds();
    int getAvailableSeats(Long eventId);

    Event createEvent(Event event);
    Event getEventById(Long id);

}
