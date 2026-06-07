package com.example.FullAPIdemo.model.dto;

import com.example.FullAPIdemo.model.entity.Marmiteria;

public class MarmiteriaResponse {

    private final Long id;
    private final String nome;
    private final String telefone;
    private final String cep;
    private final boolean status;

    public MarmiteriaResponse(Marmiteria m) {
        this.id       = m.getId();
        this.nome     = m.getNome();
        this.telefone = m.getTelefone();
        this.cep      = m.getCep();
        this.status   = Boolean.TRUE.equals(m.getStatus());
    }

    public Long getId()       { return id; }
    public String getNome()   { return nome; }
    public String getTelefone() { return telefone; }
    public String getCep()    { return cep; }
    public boolean isStatus() { return status; }
}