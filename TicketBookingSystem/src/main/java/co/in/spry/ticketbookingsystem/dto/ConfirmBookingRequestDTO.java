package co.in.spry.ticketbookingsystem.dto;

import lombok.Getter;
import lombok.Setter;

public class ConfirmBookingRequestDTO {
    private Long holdId;

    public Long getHoldId() {
        return holdId;
    }

    public void setHoldId(Long holdId) {
        this.holdId = holdId;
    }
    // Getter & Setter
}
