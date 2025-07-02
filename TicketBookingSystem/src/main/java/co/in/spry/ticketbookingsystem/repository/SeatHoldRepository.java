package co.in.spry.ticketbookingsystem.repository;

import co.in.spry.ticketbookingsystem.entity.SeatHold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface SeatHoldRepository extends JpaRepository<SeatHold, Long> {
    List<SeatHold> findByEventIdAndConfirmedFalse(Long eventId);

    // Updated method: global expired holds cleanup
    List<SeatHold> findByConfirmedFalseAndCreatedAtBefore(LocalDateTime cutoff);
}
