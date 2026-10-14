package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.dto.web.ContaResponse;
import com.example.FullAPIdemo.model.entity.LeadComercial;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.LeadComercialRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContaService {
    private final MarmiteriaRepository marmiterias;
    private final LeadComercialRepository leads;

    public ContaService(MarmiteriaRepository marmiterias, LeadComercialRepository leads) { this.marmiterias = marmiterias; this.leads = leads; }

    /** O ID vem exclusivamente do token, eliminando IDOR. */
    public ContaResponse minhaConta(Long marmiteriaId) {
        Marmiteria m = marmiterias.findById(marmiteriaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta nao encontrada."));
        LeadComercial l = leads.findByMarmiteriaId(marmiteriaId).orElse(null);
        return new ContaResponse(m.getId(), m.getNome(), m.getEmail(), m.getTelefone(), m.getCnpj(), m.getCep(),
                m.getRua(), m.getNumero(), m.getBairro(), m.getCidade(), m.getUf(),
                l != null ? l.getResponsavel() : null,
                l != null ? l.getStatusComercial().name() : null,
                l != null ? l.getStatusPagamento().name() : null,
                m.getStatusAcesso() != null ? m.getStatusAcesso().name() : "ATIVO");
    }
}
