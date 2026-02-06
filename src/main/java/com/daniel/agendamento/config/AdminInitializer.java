package com.daniel.agendamento.config;

import com.daniel.agendamento.entities.User;
import com.daniel.agendamento.enums.Role;
import com.daniel.agendamento.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            boolean adminExists =
                    userRepository.existsByEmail("admin@admin.com");

            if (!adminExists) {

                User admin = new User();
                admin.setName("Administrador");
                admin.setEmail("admin@admin.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println(" ADMIN criado com sucesso");
            }
        };
    }
}
