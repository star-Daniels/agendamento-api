package com.daniel.agendamento.controllers;

import com.daniel.agendamento.dtos.auth.LoginRequestDTO;
import com.daniel.agendamento.dtos.auth.RegisterRequestDTO;
import com.daniel.agendamento.dtos.AuthResponseDTO;
import com.daniel.agendamento.entities.User;
import com.daniel.agendamento.security.JwtService;
import com.daniel.agendamento.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO dto) {

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        User created = userService.register(user);

        String token = jwtService.generateToken(created.getEmail());

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        authenticationManager.authenticate(authToken);

        String token = jwtService.generateToken(dto.email());

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
