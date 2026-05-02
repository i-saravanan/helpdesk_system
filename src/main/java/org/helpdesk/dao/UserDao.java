package org.helpdesk.dao;

import org.helpdesk.entity.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.helpdesk.entity.User;
import org.helpdesk.util.HibernateUtil;
import org.hibernate.query.SelectionQuery;

import java.util.List;

public class UserDao {
    public void saveUser(User user){
        Session session = null;
        Transaction tx = null;
        try{
            //get session from SessionFactory
            session = HibernateUtil.getSessionFactory().openSession();
            //begin tx
            tx = session.beginTransaction();
            //persist
            session.persist(user);
            //commit
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            throw e;
        }finally {
            if(session != null)  session.close();
        }
    }
    public User findById(int id){
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(User.class, id);
        } catch (Exception e) {
            throw e;
        }finally {
            if(session != null) session.close();
        }
    }
    public User findByEmail(String email){
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            SelectionQuery<User> query = session.createSelectionQuery("FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            List<User> user = query.getResultList();
            if(user.isEmpty()) return null;
            else return user.getFirst();
        }finally {
            if(session != null) session.close();
        }
    }
    public List<User> getAllAgents(){
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            SelectionQuery<User> query = session.createSelectionQuery("FROM User u WHERE u.role = :role", User.class);
            query.setParameter("role", Role.AGENT);
            List<User> agents = query.getResultList();
            return agents;
        }finally {
            if(session!=null) session.close();
        }
    }
}
