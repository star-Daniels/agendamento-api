package com.daniel.agendamento.controllers;
import com.daniel.agendamento.dto.UserResponseDTO;
import com.daniel.agendamento.entities.User;

import com.daniel.agendamento.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserResponseDTO> register(@RequestBody User user) {

        User created = userService.register(user);

        UserResponseDTO dto = new UserResponseDTO(
                created.getId(),
                created.getName(),
                created.getEmail(),
                created.getRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
