package org.helpdesk.service;

import org.helpdesk.dao.TicketDao;
import org.helpdesk.dao.UserDao;
import org.helpdesk.entity.Role;
import org.helpdesk.entity.Ticket;
import org.helpdesk.entity.TicketStatus;
import org.helpdesk.entity.User;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketService {
    UserDao userDao;
    TicketDao ticketDao;
    public TicketService(){
        this.userDao = new UserDao();
        this.ticketDao = new TicketDao();
    }
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());
    public Ticket createTicket(User user,String title,String description){
        try{
            if(user != null && user.getRole() == Role.USER){
                Ticket ticket = new Ticket(title, description, user);
                ticketDao.createTicket(ticket);
                return ticket;
            }
            else return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in create ticket", e);
            return null;
        }
    }
    public List<Ticket> viewOwnTickets(User user){
        try{
            List<Ticket> tickets = ticketDao.getTicketsByUser(user);
            return tickets;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error in view tickets", e);
            return Collections.emptyList();
        }
    }
    public List<Ticket> viewAssignedTickets(User agent){
        try{
            if(agent.getRole() == Role.AGENT){
                return ticketDao.getTicketsByAgent(agent);
            }
            else return Collections.emptyList();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error in view assigned tickets", e);
            return Collections.emptyList();
        }
    }
    public boolean assignTicketToAgent(User admin, int ticketid, User agent){
        try{
            Ticket ticket = ticketDao.findTicketById(ticketid);
            if(admin.getRole() == Role.ADMIN && agent.getRole() == Role.AGENT && ticket != null){
                ticketDao.assignTicketToAgent(ticketid, agent);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error in assign ticket", e);
            return false;
        }
    }
    public boolean updateTicketStatus(User agent, int ticketid, TicketStatus status){
        try{
            if(agent.getRole() == Role.AGENT){
                Ticket ticket = ticketDao.findTicketById(ticketid);
                if(ticket!=null){
                    if(ticket.getAssignedTo() != null && ticket.getAssignedTo().getId() == agent.getId()){
                    ticketDao.updateTicketStatus(ticketid, status);
                    return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error in update status", e);
            return false;
        }
    }
    public List<Ticket> viewAllTickets(User admin){
        try{
            if(admin.getRole() == Role.ADMIN){
                List<Ticket> allTickets = ticketDao.getAllTickets();
                return allTickets;
            }
            else return Collections.emptyList();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error in view tickets", e);
            return Collections.emptyList();
        }
    }
    public User findAgentByEmail(String email){
        try {
            User agent = userDao.findByEmail(email);
            return agent;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error in finding agent", e);
            return null;
        }
    }
}
