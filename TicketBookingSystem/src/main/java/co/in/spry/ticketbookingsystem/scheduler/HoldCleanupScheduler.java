package co.in.spry.ticketbookingsystem.scheduler;

import co.in.spry.ticketbookingsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HoldCleanupScheduler {

    @Autowired
    private final BookingService bookingService;

    public HoldCleanupScheduler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void cleanupTask() {
        bookingService.releaseExpiredHolds();
    }
}
