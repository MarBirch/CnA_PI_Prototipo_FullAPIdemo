package com.example.FullAPIdemo.model.dto.web;

public record ContaResponse(Long marmiteriaId, String nome, String email, String telefone, String cnpj,
        String cep, String rua, String numero, String bairro, String cidade, String uf,
        String responsavel, String statusComercial, String statusPagamento, String statusAcesso) {}
