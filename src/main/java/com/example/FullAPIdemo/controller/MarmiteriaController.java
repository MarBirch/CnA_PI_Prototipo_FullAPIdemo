package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import com.example.FullAPIdemo.service.MarmiteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/apiMarmiteria")
public class MarmiteriaController {
    @Autowired
    MarmiteriaService marmiteriaService;

    @PostMapping("/inserir")
    public void inserirMarmiteria(@RequestBody Marmiteria ma) {
        marmiteriaService.inserirMarmiteria(ma);
    }

    @GetMapping("/todos")
    public List<Marmiteria> buscarTodosMarmiterias() {
        return marmiteriaService.buscarTodosMarmiterias();
    }

    @GetMapping("/buscar/{id}")
    public Marmiteria buscarPorCodigo(@PathVariable(value = "id") Long id) {
        return marmiteriaService.buscarPorCodigo(id);
    }

    @DeleteMapping("/remover/{id}")
    public void removerPorCodigo(@PathVariable(value = "id") Long id) {
        marmiteriaService.removerPorCodigo(id);
    }

    @DeleteMapping("/remover")
    public void removerPorObj (@RequestBody Marmiteria ma) {
        marmiteriaService.removerPorObj(ma);
    }

    @PutMapping("/atualizar")
    public void atualizarMarmiteria(@RequestBody Marmiteria ma) {
       marmiteriaService.atualizarMarmiteria(ma);
    }

}
