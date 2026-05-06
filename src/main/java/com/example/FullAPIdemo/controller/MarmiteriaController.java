package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import com.example.FullAPIdemo.service.MarmiteriaService;
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
    MarmiteriaService marmiteriaService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return marmiteriaService.login(req);
    }

    @PostMapping("/inserir")
    public void inserirMarmiteria(@RequestBody Marmiteria ma) {
        marmiteriaService.inserirMarmiteria(ma);
    }

    @GetMapping("/todos")
    public List<Marmiteria> buscarTodosMarmiterias() {
        return marmiteriaService.buscarTodosMarmiterias();
    }

    @DeleteMapping("/remover/{id}")
    public void removerPorCodigo(@PathVariable Long id) {
        marmiteriaService.removerPorCodigo(id);
    }

    @PutMapping("/atualizar")
    public void atualizarMarmiteria(@RequestBody Marmiteria ma) {
        marmiteriaService.atualizarMarmiteria(ma);
    }
}