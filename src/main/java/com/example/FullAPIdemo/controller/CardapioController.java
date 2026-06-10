package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.CardapioRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import com.example.FullAPIdemo.service.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apiCardapio")
@CrossOrigin(origins = "*")
public class CardapioController {

    @Autowired
    CardapioService cardapioService;
    @PostMapping("/inserir")
    public ResponseEntity<?> inserirCardapio(@RequestBody Cardapio ca) {
        return cardapioService.inserirCardapio(ca);
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return cardapioService.buscarPorId(id);
    }

    @GetMapping("/todos")
    public List<Cardapio> buscarPorMarmiteria(@RequestParam Long marmiteriaId) {
        return cardapioService.buscarPorMarmiteria(marmiteriaId);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarCardapio(@RequestBody Cardapio ca) {
        return cardapioService.atualizarCardapio(ca);
    }

    @PatchMapping("/aberto/{id}")
    public ResponseEntity<?> alterarAberto(@PathVariable Long id, @RequestParam Boolean aberto) {
        return cardapioService.alterarAberto(id, aberto);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        return cardapioService.removerPorId(id);
    }
}