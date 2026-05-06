package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class CardapioService {
    @Autowired
    CardapioRepository caRepo;

    public void inserirCardapio(@RequestBody Cardapio ca) {
        caRepo.save(ca);
    }

    public List<Cardapio> buscarTodosCardapios() {
        return caRepo.findAll();
    }

    public Optional<Cardapio> buscarPorCodigo(@PathVariable(value = "id") Long id) {
        return caRepo.findById(id);
    }

    public void removerPorCodigo(@PathVariable(value = "id") Long id) {
        caRepo.deleteById(id);
    }

    public void removerPorObj (@RequestBody Cardapio ca) {
        caRepo.delete(ca);
    }

    public void atualizarCardapio(@RequestBody Cardapio ca) {
        this.caRepo.save(ca);
    }
}
