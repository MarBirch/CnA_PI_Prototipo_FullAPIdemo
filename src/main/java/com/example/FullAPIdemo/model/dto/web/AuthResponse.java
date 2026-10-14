package com.example.FullAPIdemo.model.dto.web;

public record AuthResponse(String token, long expiraEmSegundos, String papel, Long id, String nome, String email) {}
