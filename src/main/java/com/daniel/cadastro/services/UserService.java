package com.daniel.cadastro.services;

import com.daniel.cadastro.dtos.auth.RegisterRequestDTO;
import com.daniel.cadastro.dtos.user.DeleteUserDTO;
import com.daniel.cadastro.dtos.user.UpdateUserDTO;
import com.daniel.cadastro.dtos.user.UserResponseDTO;
import com.daniel.cadastro.entities.User;
import com.daniel.cadastro.enums.Role;
import com.daniel.cadastro.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
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

    public List<UserResponseDTO> findAll(String loggedEmail) {

        User loggedUser = findByEmail(loggedEmail);

        boolean isAdmin = loggedUser.getRole().name().equals("ADMIN");

        if (!isAdmin) {
            throw new RuntimeException("Acesso negado");
        }

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();
    }

    public UserResponseDTO findById(Long id, String loggedEmail) {

        User loggedUser = findByEmail(loggedEmail);

        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean isAdmin = loggedUser.getRole().name().equals("ADMIN");
        boolean isOwner = loggedUser.getId().equals(id);

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("Acesso negado");
        }

        return new UserResponseDTO(
                targetUser.getId(),
                targetUser.getName(),
                targetUser.getEmail(),
                targetUser.getRole()
        );
    }

    public UserResponseDTO update(
            Long id,
            UpdateUserDTO dto,
            String loggedEmail
    ) {

        User loggedUser = findByEmail(loggedEmail);

        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean isAdmin = loggedUser.getRole().name().equals("ADMIN");
        boolean isOwner = loggedUser.getId().equals(id);

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("Acesso negado");
        }

        targetUser.setName(dto.getName());
        targetUser.setEmail(dto.getEmail());

        User updated = userRepository.save(targetUser);

        return new UserResponseDTO(
                updated.getId(),
                updated.getName(),
                updated.getEmail(),
                updated.getRole()
        );
    }

    public void delete(
            Long id,
            DeleteUserDTO dto,
            String loggedEmail
    ) {

        User loggedUser = findByEmail(loggedEmail);

        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean isAdmin = loggedUser.getRole().name().equals("ADMIN");
        boolean isOwner = loggedUser.getId().equals(id);

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("Acesso negado");
        }

        if (!targetUser.getEmail().equals(dto.getEmail())) {
            throw new RuntimeException("Email incorreto");
        }

        if (!passwordEncoder.matches(dto.getPassword(), targetUser.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        userRepository.delete(targetUser);
    }
}