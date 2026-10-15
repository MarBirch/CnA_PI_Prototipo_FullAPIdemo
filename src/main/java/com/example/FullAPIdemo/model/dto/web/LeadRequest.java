package com.example.FullAPIdemo.model.dto.web;

import jakarta.validation.constraints.*;

/** Payload do formulario comercial. Campos explicitos evitam mass assignment. */
public record LeadRequest(
        @NotBlank(message = "Informe o nome do estabelecimento.") @Size(max = 150) String nomeLoja,
        @Pattern(regexp = "^$|^\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}$", message = "CNPJ invalido.") String cnpj,
        @NotBlank(message = "Informe o e-mail.") @Email(message = "E-mail invalido.") @Size(max = 150) String email,
        @NotBlank(message = "Informe o telefone.") @Pattern(regexp = "^[0-9()+\\s-]{8,20}$", message = "Telefone invalido.") String telefone,
        @NotBlank(message = "Informe o responsavel.") @Size(max = 120) String responsavel,
        @Size(max = 100) String cidade,
        @Pattern(regexp = "^$|^[A-Za-z]{2}$", message = "UF invalida.") String uf,
        @Size(max = 1000) String mensagem
) {}
