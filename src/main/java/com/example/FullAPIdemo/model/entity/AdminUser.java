package com.example.FullAPIdemo.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/** Usuario administrativo. Senha sempre armazenada como hash BCrypt. */
@Entity
@Table(name = "admin_user")
public class AdminUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 150) private String email;
    @Column(nullable = false, length = 120) private String nome;
    @Column(name = "senha_hash", nullable = false, length = 100) private String senhaHash;
    @Column(nullable = false) private boolean ativo = true;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean v) { ativo = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }
    public String getNome() { return nome; }
    public void setNome(String v) { nome = v; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String v) { senhaHash = v; }
}
