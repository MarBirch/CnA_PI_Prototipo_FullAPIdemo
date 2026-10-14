package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.web.ContaResponse;
import com.example.FullAPIdemo.security.CurrentUser;
import com.example.FullAPIdemo.service.ContaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conta")
public class ContaController {
    private final ContaService contas;

    public ContaController(ContaService contas) { this.contas = contas; }

    @GetMapping
    public ContaResponse minhaConta() { return contas.minhaConta(CurrentUser.get().id()); }
}
