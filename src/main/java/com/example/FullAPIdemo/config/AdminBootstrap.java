package com.example.FullAPIdemo.config;

import com.example.FullAPIdemo.model.entity.AdminUser;
import com.example.FullAPIdemo.repository.AdminUserRepository;
import com.example.FullAPIdemo.security.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** Cria o primeiro administrador a partir de ADMIN_EMAIL / ADMIN_PASSWORD, se nenhum existir. */
@Component
public class AdminBootstrap implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);
    private final AdminUserRepository repo;
    private final PasswordService passwords;
    private final String email;
    private final String senha;

    public AdminBootstrap(AdminUserRepository repo, PasswordService passwords,
                          @Value("${app.admin.bootstrap-email:}") String email,
                          @Value("${app.admin.bootstrap-password:}") String senha) {
        this.repo = repo; this.passwords = passwords; this.email = email; this.senha = senha;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (repo.count() > 0 || email.isBlank() || senha.isBlank()) return;
        if (senha.length() < 10) { log.warn("ADMIN_PASSWORD precisa ter ao menos 10 caracteres; admin nao criado."); return; }
        AdminUser a = new AdminUser();
        a.setEmail(email.trim().toLowerCase());
        a.setNome("Administrador");
        a.setSenhaHash(passwords.hash(senha));
        repo.save(a);
        log.info("Administrador inicial criado.");
    }
}
