package com.example.FullAPIdemo.controller;
import com.example.FullAPIdemo.model.dto.web.ContaResponse;
import com.example.FullAPIdemo.model.dto.web.EnderecoRequest;
import com.example.FullAPIdemo.security.CurrentUser;
import com.example.FullAPIdemo.service.ContaService;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/conta")
public class ContaController {
 private final ContaService contas;
 public ContaController(ContaService contas){this.contas=contas;}
 @PatchMapping("/endereco") public ContaResponse endereco(@RequestBody @jakarta.validation.Valid EnderecoRequest r){return contas.atualizarEndereco(CurrentUser.get().id(),r);}
 @GetMapping public ContaResponse minhaConta(){return contas.minhaConta(CurrentUser.get().id());}
}
