package org.helpdesk.service;

import org.helpdesk.dao.UserDao;
import org.helpdesk.entity.User;
import org.helpdesk.entity.Role;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Objects;

public class AuthService {
    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    UserDao userDao;
    public AuthService(){
        this.userDao = new UserDao();
    }
    public User registerUser(String name, String email, String password, Role role){
        try{
            if(userDao.findByEmail(email) == null){
                User user = new User(name, email, password, role);
                userDao.saveUser(user);
                return user;
            }else return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in registerUser", e);
            return null;
        }
    }
    public User loginUser(String email, String password){
        try{
            User user = userDao.findByEmail(email);
            if(user != null && Objects.equals(user.getPassword(), password)) return user;
            else return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in loginUser", e);
            return null;
        }
    }
}
