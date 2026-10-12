package com.deviceauth.repository;
import com.deviceauth.entity.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<AppUser,Long> { Optional<AppUser> findByEmail(String email); boolean existsByEmail(String email); }
