package com.example.FullAPIdemo.model.dto.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarStatusRequest(@NotBlank String status, @Size(max = 1000) String observacao) {}
