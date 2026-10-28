package com.example.FullAPIdemo.model.dto.web;
import jakarta.validation.constraints.*;
/** Explicit registration payload prevents mass assignment. */
public record LeadRequest(
 @NotBlank(message="Informe o nome do estabelecimento.") @Size(max=150) String nomeLoja,
 @Pattern(regexp="^$|^\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}$",message="CNPJ invalido.") String cnpj,
 @NotBlank @Email @Size(max=150) String email,
 @NotBlank @Pattern(regexp="^(?:\\d{10,11}|\\(\\d{2}\\) \\d{4,5}-\\d{4})$",message="Informe o telefone com DDD.") String telefone,
 @NotBlank @Size(max=120) String responsavel,
 @Pattern(regexp="^$|^\\d{5}-?\\d{3}$",message="CEP invalido.") @Size(max=9) String cep,
 @Size(max=255) String numero, @Size(max=1000) String mensagem,
 @Size(max=255) String rua, @Size(max=255) String bairro, @Size(max=100) String cidade,
 @Pattern(regexp="^$|^[A-Za-z]{2}$",message="UF invalida.") String uf
) {}
