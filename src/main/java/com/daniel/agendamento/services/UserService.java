package com.daniel.agendamento.services;

import com.daniel.agendamento.dtos.auth.RegisterRequestDTO;
import com.daniel.agendamento.dtos.user.UserResponseDTO;
import com.daniel.agendamento.entities.User;
import com.daniel.agendamento.enums.Role;
import com.daniel.agendamento.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(RegisterRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);

        User saved = userRepository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole()
        );
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
