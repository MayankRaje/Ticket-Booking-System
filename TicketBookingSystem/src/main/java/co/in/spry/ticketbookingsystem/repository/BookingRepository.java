package co.in.spry.ticketbookingsystem.repository;

import co.in.spry.ticketbookingsystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByEventIdAndCanceledFalse(Long eventId);
}
