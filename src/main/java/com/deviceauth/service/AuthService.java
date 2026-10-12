package com.deviceauth.service;

import com.deviceauth.dto.*;import com.deviceauth.entity.*;import com.deviceauth.repository.UserRepository;import com.deviceauth.security.JwtService;
import org.springframework.http.HttpStatus;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Service;import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
 private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt;
 public AuthService(UserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 public AuthResponse register(RegisterRequest r){String email=r.email().trim().toLowerCase();if(users.existsByEmail(email))throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already registered");AppUser u=new AppUser();u.setName(r.name().trim());u.setEmail(email);u.setPasswordHash(encoder.encode(r.password()));u.setRole(Role.USER);u=users.save(u);return response(u);}
 public AuthResponse login(LoginRequest r){AppUser u=users.findByEmail(r.email().trim().toLowerCase()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));if(!encoder.matches(r.password(),u.getPasswordHash()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");return response(u);}
 private AuthResponse response(AppUser u){return new AuthResponse(jwt.generate(u.getEmail(),u.getRole().name()),u.getId(),u.getName(),u.getEmail(),u.getRole().name());}
}
