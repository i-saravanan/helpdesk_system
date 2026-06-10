package org.helpdesk.controller;

import jakarta.validation.Valid;

import org.helpdesk.dto.StatusRequest;
import org.helpdesk.dto.TicketRequest;
import org.helpdesk.entity.Ticket;
import org.helpdesk.entity.User;
import org.helpdesk.exception.UserNotFoundException;
import org.helpdesk.repository.UserRepository;
import org.helpdesk.service.TicketService;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final UserRepository userRepository;
    public TicketController(TicketService ticketService, UserRepository userRepository){
        this.userRepository = userRepository;
        this.ticketService = ticketService;
    }

    @PostMapping("/create/{userId}")
    public Ticket create(@Valid @RequestBody TicketRequest request, @PathVariable Integer userId){
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        ()-> new UserNotFoundException("User Not Found.")
                );
        return ticketService.createTicket(user, request.getTitle(),request.getDescription());
    }
    @GetMapping("/user/{userId}")
    public List<Ticket> viewAllTicketsByUser(@PathVariable Integer userId){
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        ()-> new UserNotFoundException("User Not Found.")
                );
        return ticketService.viewOwnTickets(user);
    }
    @GetMapping("/agent/{agentId}")
    public List<Ticket> viewAssignedTickets(@PathVariable Integer agentId){
        User user = userRepository
                .findById(agentId)
                .orElseThrow(
                        ()-> new UserNotFoundException("User Not Found.")
                );
        return ticketService.viewAssignedTickets(user);
    }
    @Transactional
    @PutMapping("/{ticketId}/assign/{agentId}/{adminId}")
    public ResponseEntity<String> assignTicketToAgent(@PathVariable Integer ticketId, @PathVariable Integer agentId,@PathVariable Integer adminId){
        User admin = userRepository
                .findById(adminId)
                .orElseThrow(
                        ()-> new UserNotFoundException("User Not Found.")
                );
        User agent = userRepository
                .findById(agentId)
                .orElseThrow(
                        ()-> new UserNotFoundException("User Not Found.")
                );
        boolean assignticket = ticketService.assignTicketToAgent(admin,ticketId,agent);
        if(assignticket) return ResponseEntity.ok("Ticket is assigned to agent.");
        return ResponseEntity.notFound().build();
    }
    @Transactional
    @PutMapping("/{ticketId}/{status}/{agentId}")
    public ResponseEntity<String> updateTicketStatus(@PathVariable Integer ticketId, @PathVariable Integer agentId, @RequestBody StatusRequest request) throws Exception {
        User agent = userRepository
                .findById(agentId)
                .orElseThrow(
                        ()-> new UserNotFoundException("User Not Found.")
                );
        boolean updateStatus = ticketService.updateTicketStatus(agent,ticketId,request.getStatus());
        if(updateStatus) return ResponseEntity.ok("Update Ticket Status Successfully.");
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/admin/{adminId}")
    public List<Ticket> viewAllTickets(@PathVariable Integer adminId){
        User admin = userRepository
                .findById(adminId)
                .orElseThrow(
                        ()-> new UserNotFoundException("User Not Found.")
                );
        return ticketService.viewAllTickets(admin);
    }

}
