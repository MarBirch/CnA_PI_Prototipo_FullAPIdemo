package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/apiCardapio")
public class CardapioController {
    @Autowired
    CardapioRepository caRepo;

    @PostMapping("/inserir")
    public void inserirCardapio(@RequestBody Cardapio ca) {
        caRepo.save(ca);
    }

    @GetMapping("/todos")
    public List<Cardapio> buscarTodosCardapios() {
        return caRepo.findAll();
    }

    @GetMapping("/buscar/{id}/")
    public Optional<Cardapio> buscarPorCodigo(@PathVariable(value = "id") Long id) {
        return caRepo.findById(id);
    }

    @DeleteMapping("/remover/{id}")
    public void removerPorCodigo(@PathVariable(value = "id") Long id) {
        caRepo.deleteById(id);
    }

    @DeleteMapping("/remover")
    public void removerPorObj (@RequestBody Cardapio ca) {
        caRepo.delete(ca);
    }

    @PutMapping("/atualizar")
    public void atualizarCardapio(@RequestBody Cardapio ca) {
        this.caRepo.save(ca);
    }

}
