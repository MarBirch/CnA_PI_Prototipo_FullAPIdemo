package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/apiMarmiteria")
@CrossOrigin(origins = "*")
public class MarmiteriaController {

    @Autowired
    MarmiteriaRepository maRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<Marmiteria> opt = maRepo.findByEmailAndSenha(req.getUsername(), req.getSenha());
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos.");
        }
        Marmiteria m = opt.get();
        Map<String, Object> resp = new HashMap<>();
        resp.put("id", m.getId());
        resp.put("nome", m.getNome());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/inserir")
    public void inserirMarmiteria(@RequestBody Marmiteria ma) {
        maRepo.save(ma);
    }

    @GetMapping("/todos")
    public List<Marmiteria> buscarTodosMarmiterias() {
        return maRepo.findAll();
    }

    @DeleteMapping("/remover/{id}")
    public void removerPorCodigo(@PathVariable Long id) {
        maRepo.deleteById(id);
    }

    @PutMapping("/atualizar")
    public void atualizarMarmiteria(@RequestBody Marmiteria ma) {
        maRepo.save(ma);
    }
}