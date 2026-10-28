package com.example.FullAPIdemo.model.dto.web;
import jakarta.validation.constraints.*;
/** Only address fields are writable; identity, password and commercial states are excluded. */
public record EnderecoRequest(
 @Pattern(regexp="^$|^\\d{5}-?\\d{3}$", message="CEP invalido.") @Size(max=9) String cep,
 @Size(max=255) String numero, @Size(max=255) String rua,
 @Size(max=255) String bairro, @Size(max=100) String cidade,
 @Pattern(regexp="^$|^[A-Za-z]{2}$", message="UF invalida.") String uf
) {}
