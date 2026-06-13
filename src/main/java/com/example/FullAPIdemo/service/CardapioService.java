package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.CardapioRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class CardapioService {
    @Autowired
    CardapioRepository caRepo;

    @Autowired
    MarmiteriaRepository maRepo;

    public ResponseEntity<?> inserirCardapio(@RequestBody Cardapio ca) {
        if (ca.getMarmiteria() == null || ca.getMarmiteria().getId() == null) {
            return ResponseEntity.badRequest().body("marmiteriaId obrigatório.");
        }
        Optional<Marmiteria> m = maRepo.findById(ca.getMarmiteria().getId());
        if (m.isEmpty()) return ResponseEntity.notFound().build();
        ca.setMarmiteria(m.get());
        if (ca.getIngredientes() != null) {
            ca.getIngredientes().forEach(i -> i.setCardapio(ca));
        }
        return ResponseEntity.status(201).body(caRepo.save(ca));
    }

    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return caRepo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public List<Cardapio> buscarPorMarmiteria(@RequestParam Long marmiteriaId) {
        return caRepo.findByMarmiteriaId(marmiteriaId);
    }

    public ResponseEntity<?> atualizarCardapio(@RequestBody Cardapio ca) {
        if (!caRepo.existsById(ca.getId())) return ResponseEntity.notFound().build();
        Cardapio existente = caRepo.findById(ca.getId()).get();
        if (ca.getMarmiteria() == null) ca.setMarmiteria(existente.getMarmiteria());
        if (ca.getIngredientes() != null) {
            ca.getIngredientes().forEach(i -> i.setCardapio(ca));
        }
        return ResponseEntity.ok(caRepo.save(ca));
    }

    public ResponseEntity<?> alterarAberto(@PathVariable Long id, @RequestParam Boolean aberto) {
        Optional<Cardapio> opt = caRepo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Cardapio c = opt.get();
        c.setAberto(aberto);
        return ResponseEntity.ok(caRepo.save(c));
    }

    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        if (!caRepo.existsById(id)) return ResponseEntity.notFound().build();
        caRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}