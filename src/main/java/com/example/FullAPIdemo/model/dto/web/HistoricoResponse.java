package com.example.FullAPIdemo.model.dto.web;

import com.example.FullAPIdemo.model.entity.LeadHistorico;

import java.time.LocalDateTime;

public record HistoricoResponse(Long id, String tipo, String descricao, String autorEmail, LocalDateTime createdAt) {
    public static HistoricoResponse of(LeadHistorico h) {
        return new HistoricoResponse(h.getId(), h.getTipo(), h.getDescricao(), h.getAutorEmail(), h.getCreatedAt());
    }
}
