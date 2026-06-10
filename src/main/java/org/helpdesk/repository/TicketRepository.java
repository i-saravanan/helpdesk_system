package org.helpdesk.repository;

import org.helpdesk.entity.Ticket;
import org.helpdesk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
        List<Ticket> findByCreatedBy(User user);
        List<Ticket> findByAssignedTo(User user);

}
