package com.monopoly.secure_spring_monopoly.service;

import com.monopoly.secure_spring_monopoly.entity.UserEntity;
import com.monopoly.secure_spring_monopoly.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : bytes) sb.append(String.format("%02x", b));

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean register(String username, String email, String password) {
        if (userRepo.findByEmail(email).isPresent()) return false;

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(hashPassword(password));

        userRepo.save(user);
        return true;
    }

    public UserEntity login(String email, String password) {
        Optional<UserEntity> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) return null;

        UserEntity user = userOpt.get();

        if (!user.getPasswordHash().equals(hashPassword(password))) return null;

        return user;
    }
}
