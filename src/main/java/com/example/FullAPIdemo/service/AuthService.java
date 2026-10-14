package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.Papel;
import com.example.FullAPIdemo.model.dto.web.AuthResponse;
import com.example.FullAPIdemo.model.dto.web.LoginWebRequest;
import com.example.FullAPIdemo.model.entity.AdminUser;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.AdminUserRepository;
import com.example.FullAPIdemo.security.AuthPrincipal;
import com.example.FullAPIdemo.security.JwtService;
import com.example.FullAPIdemo.security.PasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private static final String INVALIDO = "E-mail ou senha invalidos.";
    private final AdminUserRepository admins;
    private final MarmiteriaService marmiteriaService;
    private final PasswordService passwords;
    private final JwtService jwt;

    public AuthService(AdminUserRepository admins, MarmiteriaService marmiteriaService, PasswordService passwords, JwtService jwt) {
        this.admins = admins; this.marmiteriaService = marmiteriaService; this.passwords = passwords; this.jwt = jwt;
    }

    public AuthResponse loginAdmin(LoginWebRequest r) {
        AdminUser a = admins.findByEmailIgnoreCase(r.email().trim()).filter(AdminUser::isAtivo)
                .filter(x -> passwords.matches(r.senha(), x.getSenhaHash()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALIDO));
        AuthPrincipal p = new AuthPrincipal(a.getId(), a.getEmail(), Papel.ADMIN);
        return new AuthResponse(jwt.gerar(p), jwt.getExpirationSeconds(), Papel.ADMIN.name(), a.getId(), a.getNome(), a.getEmail());
    }

    public AuthResponse loginEmpresa(LoginWebRequest r) {
        Marmiteria m = marmiteriaService.autenticar(r.email(), r.senha())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALIDO));
        if (!m.isAcessoAtivo()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O acesso desta empresa nao esta ativo.");
        AuthPrincipal p = new AuthPrincipal(m.getId(), m.getEmail(), Papel.EMPRESA);
        return new AuthResponse(jwt.gerar(p), jwt.getExpirationSeconds(), Papel.EMPRESA.name(), m.getId(), m.getNome(), m.getEmail());
    }
}
