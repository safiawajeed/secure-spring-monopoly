package com.monopoly.secure_spring_monopoly.controller;

import com.monopoly.secure_spring_monopoly.entity.UserEntity;
import com.monopoly.secure_spring_monopoly.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody Map<String,String> body) {
        boolean ok = authService.register(body.get("username"), body.get("email"), body.get("password"));
        return Map.of("success", ok);
    }

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String,String> body, HttpSession session) {
        UserEntity user = authService.login(body.get("email"), body.get("password"));
        if (user == null) return Map.of("success", false);

        session.setAttribute("userId", user.getId());
        return Map.of("success", true, "userId", user.getId());
    }

    @GetMapping("/whoami")
    public Map<String,Object> whoami(HttpSession session) {
        Object id = session.getAttribute("userId");
        if (id == null) return Map.of("loggedIn", false);
        return Map.of("loggedIn", true, "userId", id);
    }

    @PostMapping("/logout")
    public Map<String,Object> logout(HttpSession session) {
        session.invalidate();
        return Map.of("success", true);
    }
}
