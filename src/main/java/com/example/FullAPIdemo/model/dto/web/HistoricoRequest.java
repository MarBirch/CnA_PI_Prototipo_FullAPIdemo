package com.example.FullAPIdemo.model.dto.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HistoricoRequest(@NotBlank @Size(max = 1000) String descricao) {}
