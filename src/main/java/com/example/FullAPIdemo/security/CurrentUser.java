package com.example.FullAPIdemo.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

public final class CurrentUser {
    private CurrentUser() {}

    public static AuthPrincipal get() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AuthPrincipal p)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return p;
    }
}
