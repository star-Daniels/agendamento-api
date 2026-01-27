package com.daniel.agendamento.services;

import com.daniel.agendamento.entities.User;
import com.daniel.agendamento.enums.Role;
import com.daniel.agendamento.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User register(User user) {


        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }


        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }


        return userRepository.save(user);
    }
}
