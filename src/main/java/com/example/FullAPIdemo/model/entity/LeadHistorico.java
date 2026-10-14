package com.example.FullAPIdemo.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/** Registro de acoes e contatos feitos pela administracao em um lead. */
@Entity
@Table(name = "lead_historico")
public class LeadHistorico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "lead_id") private LeadComercial lead;
    @Column(nullable = false, length = 60) private String tipo;
    @Column(length = 1000) private String descricao;
    @Column(name = "autor_email", length = 150) private String autorEmail;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    public LeadHistorico() {}
    public LeadHistorico(LeadComercial lead, String tipo, String descricao, String autorEmail) {
        this.lead = lead; this.tipo = tipo; this.descricao = descricao; this.autorEmail = autorEmail;
    }

    public Long getId() { return id; }
    public LeadComercial getLead() { return lead; }
    public String getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public String getAutorEmail() { return autorEmail; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
