package com.example.FullAPIdemo.model.dto.web;

import com.example.FullAPIdemo.model.entity.LeadComercial;

import java.time.LocalDateTime;
import java.util.List;

/** Visao administrativa de um cadastro comercial. Nunca inclui senha. */
public record EmpresaResponse(
        Long id, String nomeLoja, String cnpj, String email, String telefone,
        String responsavel, String cep, String numero, String mensagem,
        String statusComercial, String statusPagamento, String statusAcesso,
        Long marmiteriaId, LocalDateTime createdAt, LocalDateTime updatedAt, List<HistoricoResponse> historico) {
    public static EmpresaResponse of(LeadComercial l, List<HistoricoResponse> historico) {
        return new EmpresaResponse(l.getId(), l.getNomeLoja(), l.getCnpj(), l.getEmail(), l.getTelefone(),
                l.getResponsavel(), l.getCep(), l.getNumero(), l.getMensagem(),
                l.getStatusComercial().name(), l.getStatusPagamento().name(), l.getStatusAcesso().name(),
                l.getMarmiteria() != null ? l.getMarmiteria().getId() : null, l.getCreatedAt(), l.getUpdatedAt(), historico);
    }
}
