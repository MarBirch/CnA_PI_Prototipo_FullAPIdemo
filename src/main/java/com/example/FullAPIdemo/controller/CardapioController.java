package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.repository.CardapioRepository;
import com.example.FullAPIdemo.service.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/apiCardapio")
public class CardapioController {
    @Autowired
    CardapioService cadapioService;

    @PostMapping("/inserir")
    public void inserirCardapio(@RequestBody Cardapio ca) {
        cadapioService.inserirCardapio(ca);
    }

    @GetMapping("/todos")
    public List<Cardapio> buscarTodosCardapios() {
        return cadapioService.buscarTodosCardapios();
    }

    @GetMapping("/buscar/{id}/")
    public Optional<Cardapio> buscarPorCodigo(@PathVariable(value = "id") Long id) {
        return cadapioService.buscarPorCodigo(id);
    }

    @DeleteMapping("/remover/{id}")
    public void removerPorCodigo(@PathVariable(value = "id") Long id) {
        cadapioService.removerPorCodigo(id);
    }

    @DeleteMapping("/remover")
    public void removerPorObj (@RequestBody Cardapio ca) {
        cadapioService.removerPorObj(ca);
    }

    @PutMapping("/atualizar")
    public void atualizarCardapio(@RequestBody Cardapio ca){cadapioService.atualizarCardapio(ca);
    }

}
