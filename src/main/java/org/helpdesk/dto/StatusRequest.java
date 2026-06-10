package org.helpdesk.dto;

import org.helpdesk.entity.TicketStatus;

public class StatusRequest {
    private TicketStatus status;

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
