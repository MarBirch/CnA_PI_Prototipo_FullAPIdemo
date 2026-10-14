package com.example.FullAPIdemo.model.dto.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginWebRequest(@NotBlank @Email @Size(max = 150) String email, @NotBlank @Size(max = 200) String senha) {}
