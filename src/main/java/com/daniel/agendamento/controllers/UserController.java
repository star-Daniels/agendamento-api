package com.daniel.agendamento.controllers;

import com.daniel.agendamento.dtos.user.UserRequestDTO;
import com.daniel.agendamento.dtos.user.UserResponseDTO;
import com.daniel.agendamento.services.UserService;
import jakarta.validation.Valid;
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


}
