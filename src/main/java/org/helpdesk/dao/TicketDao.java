package org.helpdesk.dao;

import org.helpdesk.entity.Ticket;
import org.helpdesk.entity.TicketStatus;
import org.helpdesk.entity.User;
import org.helpdesk.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketDao {
    private static final Logger logger = Logger.getLogger(TicketDao.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TicketDao.class);

    public void createTicket(Ticket ticket){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(ticket);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
            logger.log(Level.SEVERE, "Error in create ticket", e);
        }finally {
            if(session!=null) session.close();
        }
    }
    public Ticket findTicketById(int id){
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Ticket.class, id);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error in the find ticket", e);
            return null;
        }finally {
            if(session!=null) session.close();
        }
    }
    public List<Ticket> getTicketsByUser(User user){
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            SelectionQuery<Ticket> query = session.createSelectionQuery("FROM Ticket t WHERE t.createdBy = :user", Ticket.class);
            query.setParameter("user",user);
            List<Ticket> tickets = query.getResultList();
            return tickets;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in get tickets", e);
            return Collections.emptyList();
        }finally {
            if (session != null) session.close();
        }
    }
    public List<Ticket> getTicketsByAgent(User user){
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            SelectionQuery<Ticket> query = session.createSelectionQuery("FROM Ticket t WHERE t.assignedTo = :user", Ticket.class);
            query.setParameter("user",user);
            List<Ticket> tickets = query.getResultList();
            return tickets;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in get tickets", e);
            return Collections.emptyList();
        }finally {
            if(session!=null) session.close();
        }
    }
    public void assignTicketToAgent(int ticketid, User agent){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, ticketid);
            ticket.setAssignedTo(agent);
            tx.commit();
        } catch (Exception e) {
            if(tx!=null) tx.rollback();
            logger.log(Level.SEVERE,"Error in assign ticket", e);
        }finally {
            if(session!=null) session.close();
        }
    }
    public void updateTicketStatus(int ticketid, TicketStatus newStatus){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.get(Ticket.class, ticketid).setStatus(newStatus);
            tx.commit();
        } catch (Exception e) {
            if(tx!=null) tx.rollback();
            logger.log(Level.SEVERE,"Error in update ticket status", e);
        }finally {
            if(session!=null) session.close();
        }
    }
    public List<Ticket> getAllTickets(){
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            SelectionQuery<Ticket> query = session.createSelectionQuery("FROM Ticket", Ticket.class);
            List<Ticket> allTickets = query.getResultList();
            return allTickets;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error in getting tickets", e);
            return Collections.emptyList();
        }finally {
            if(session != null) session.close();
        }
    }
}
