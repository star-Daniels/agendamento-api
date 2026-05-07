package com.daniel.cadastro.controllers;

import com.daniel.cadastro.dtos.auth.AuthResponseDTO;
import com.daniel.cadastro.dtos.auth.LoginRequestDTO;
import com.daniel.cadastro.dtos.auth.RegisterRequestDTO;
import com.daniel.cadastro.dtos.user.UserResponseDTO;
import com.daniel.cadastro.entities.User;
import com.daniel.cadastro.security.JwtService;
import com.daniel.cadastro.services.UserService;
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


        UserResponseDTO created = userService.register(dto);

        String token = jwtService.generateToken(created.getEmail());

        return ResponseEntity.ok(
                new AuthResponseDTO(
                        token,
                        created.getId(),
                        created.getName(),
                        created.getEmail(),
                        created.getRole().name()
                )
        );
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        User user = userService.findByEmail(dto.getEmail());

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(
                new AuthResponseDTO(
                        token,
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole().name()
                )
        );
    }
}
