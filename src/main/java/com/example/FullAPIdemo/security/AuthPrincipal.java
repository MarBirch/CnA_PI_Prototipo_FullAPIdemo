package com.example.FullAPIdemo.security;

import com.example.FullAPIdemo.model.Papel;

/** Identidade autenticada extraida do token. Nunca vem do corpo da requisicao. */
public record AuthPrincipal(Long id, String email, Papel papel) {}
