package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.web.AuthResponse;
import com.example.FullAPIdemo.model.dto.web.LoginWebRequest;
import com.example.FullAPIdemo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService auth;

    public AuthController(AuthService auth) { this.auth = auth; }

    @PostMapping("/admin/login")
    public AuthResponse admin(@RequestBody @Valid LoginWebRequest r) { return auth.loginAdmin(r); }

    @PostMapping("/empresa/login")
    public AuthResponse empresa(@RequestBody @Valid LoginWebRequest r) { return auth.loginEmpresa(r); }
}
