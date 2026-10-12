package com.deviceauth;

import com.deviceauth.entity.AppUser;
import com.deviceauth.entity.Role;
import com.deviceauth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DeviceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeviceAuthApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String[] adminEmails = {"admin@deviceauth.com", "admin@example.com"};
            for (String email : adminEmails) {
                if (userRepository.findByEmail(email).isEmpty()) {
                    AppUser admin = new AppUser();
                    admin.setName("System Administrator");
                    admin.setEmail(email);
                    admin.setPasswordHash(passwordEncoder.encode("Admin@12345"));
                    admin.setRole(Role.ADMIN);
                    userRepository.save(admin);
                    System.out.println("Default Admin account seeded: " + email);
                }
            }
        };
    }
}
