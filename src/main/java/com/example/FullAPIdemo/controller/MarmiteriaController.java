package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.dto.MarmiteriaResponse;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.service.MarmiteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    /** Retorna todas as marmiterias via DTO seguro (sem risco de loop de serialização). */
    @GetMapping("/todos")
    public List<MarmiteriaResponse> buscarTodosMarmiterias() {
        return marmiteriaService.buscarTodosMarmiterias()
                .stream()
                .map(MarmiteriaResponse::new)
                .collect(Collectors.toList());
    }

    /** Retorna apenas as marmiterias abertas (status = true). */
    @GetMapping("/abertos")
    public List<MarmiteriaResponse> buscarMarmiteriasAbertas() {
        return marmiteriaService.buscarMarmiteriasAbertas()
                .stream()
                .map(MarmiteriaResponse::new)
                .collect(Collectors.toList());
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