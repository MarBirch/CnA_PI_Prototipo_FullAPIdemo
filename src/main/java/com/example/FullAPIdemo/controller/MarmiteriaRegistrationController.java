package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cadastro-marmiteria")
public class MarmiteriaRegistrationController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar-email")
    public void enviarEmailCadastro(@RequestBody Map<String, String> data) {
        String body = "Novo cadastro de restaurante:\n\n" +
                "Nome da Loja: " + data.get("nomeLoja") + "\n" +
                "CNPJ: " + data.get("cnpj") + "\n" +
                "Email: " + data.get("email") + "\n" +
                "Telefone: " + data.get("telefone") + "\n" +
                "Tipo de Estabelecimento: " + data.get("tipoEstabelecimento");

        emailService.sendEmail("cl203257@g.unicamp.br", "Novo Cadastro de Restaurante", body);
    }
}
