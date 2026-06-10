package org.helpdesk.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.helpdesk.entity.TicketStatus;

public class TicketRequest {
    @NotBlank
    private String title;
    @Size(min=5,max=250)
    private String Description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
