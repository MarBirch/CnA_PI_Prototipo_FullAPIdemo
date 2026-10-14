package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.web.LeadRequest;
import com.example.FullAPIdemo.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/** Formulario comercial: salva o lead no banco e notifica por e-mail. /enviar-email mantido por compatibilidade. */
@RestController
@RequestMapping("/api/cadastro-marmiteria")
public class MarmiteriaRegistrationController {
    private final LeadService leadService;

    public MarmiteriaRegistrationController(LeadService leadService) { this.leadService = leadService; }

    @PostMapping({"", "/enviar-email"})
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> cadastrar(@RequestBody @Valid LeadRequest request) { return leadService.registrar(request); }
}
