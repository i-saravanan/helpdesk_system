package org.helpdesk.service;

import org.helpdesk.entity.User;
import org.helpdesk.entity.Role;
import org.helpdesk.exception.DuplicateEmailException;
import org.helpdesk.exception.UnauthorizedActionException;
import org.helpdesk.exception.UserNotFoundException;
import org.helpdesk.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(String name, String email, String password, Role role){
            if(userRepository.existsByEmail(email))
                throw new DuplicateEmailException("Email already registered.");
            User user = new User(name, email, password, role);
            userRepository.save(user);
            return user;
    }
    public User loginUser(String email, String password){
            User user = userRepository.findByEmail(email).orElseThrow(
                    ()->new UserNotFoundException(
                            "Email is not registered."
                    )
            );
            if(!user.getPassword().equals(password)) throw new UnauthorizedActionException("Password is wrong, try again.");
            return user;
    }
}
