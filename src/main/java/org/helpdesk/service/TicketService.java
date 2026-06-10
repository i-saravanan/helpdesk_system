package org.helpdesk.service;

import org.helpdesk.entity.Role;
import org.helpdesk.entity.Ticket;
import org.helpdesk.entity.TicketStatus;
import org.helpdesk.entity.User;
import org.helpdesk.exception.TicketNotFoundException;
import org.helpdesk.exception.UnauthorizedActionException;
import org.helpdesk.exception.UserNotFoundException;
import org.helpdesk.repository.TicketRepository;
import org.helpdesk.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TicketService {
    private final TicketRepository ticketRepo;
    private final UserRepository userRepository;
    public TicketService(TicketRepository ticketRepo, UserRepository userRepository){
        this.ticketRepo = ticketRepo;
        this.userRepository = userRepository;
    }
    public Ticket createTicket(User user,String title,String description){
            if(user == null || user.getRole() != Role.USER) throw new UnauthorizedActionException("Only Users can create ticket");
            Ticket ticket = new Ticket(title, description, user);
            return ticketRepo.save(ticket);
    }
    public List<Ticket> viewOwnTickets(User user){
            if(user.getRole() != Role.USER) throw new UnauthorizedActionException("Only users can view All own tickets.");
            List<Ticket> tickets = ticketRepo.findByCreatedBy(user);
            return tickets;
    }
    public List<Ticket> viewAssignedTickets(User agent){
            if(agent.getRole() != Role.AGENT) throw new UnauthorizedActionException("Only Agents can get Assigned Tickets");
            return ticketRepo.findByAssignedTo(agent);
    }
    @Transactional
    public boolean assignTicketToAgent(
            User admin,
            int ticketid,
            User agent){
            Ticket ticket = ticketRepo.findById(ticketid)
                    .orElseThrow(
                        ()->new TicketNotFoundException(
                                "Ticket not found."
                        )
                    );
            if(admin.getRole() != Role.ADMIN || agent.getRole() != Role.AGENT)
                throw new UnauthorizedActionException(
                        "Unauthorized user."
                );
            ticket.setAssignedTo(agent);
            ticketRepo.save(ticket);
            return true;
    }
    @Transactional
    public boolean updateTicketStatus(
            User agent,
            int ticketid,
            TicketStatus status)  {
            if(agent.getRole() != Role.AGENT)
                throw new UnauthorizedActionException("Only Agents can update ticket status.");
            Ticket ticket = ticketRepo.findById(ticketid)
                    .orElseThrow(
                        ()->new TicketNotFoundException(
                            "Ticket not found."
                        )
                    );
                if(ticket.getAssignedTo() == null || ticket.getAssignedTo().getId() != agent.getId())
                    throw new UnauthorizedActionException("Not allowed to update the ticket status.");
                ticket.setStatus(status);
                ticketRepo.save(ticket);
                return true;
    }
    public List<Ticket> viewAllTickets(User admin){
            if(admin.getRole() != Role.ADMIN)
                throw new UnauthorizedActionException("Only Admins can access the all tickets.");
            return ticketRepo.findAll();
    }
    public User findAgentByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(
                    ()->new UserNotFoundException(
                            "user not found."
                    )
                );
    }
}
