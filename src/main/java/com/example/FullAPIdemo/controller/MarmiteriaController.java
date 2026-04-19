package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/apiMarmiteria")
public class MarmiteriaController {
    @Autowired
    MarmiteriaRepository maRepo;

    @PostMapping("/inserir")
    public void inserirMarmiteria(@RequestBody Marmiteria ma) {
        maRepo.save(ma);
    }

    @GetMapping("/todos")
    public List<Marmiteria> buscarTodosMarmiterias() {
        return maRepo.findAll();
    }

    @GetMapping("/busmar/{codigo}")
    public Marmiteria buscarPorCodigo(@PathVariable(value = "id") Long id) {
        return maRepo.getById(id);
    }

    @DeleteMapping("/remover/{id}")
    public void removerPorCodigo(@PathVariable(value = "id") Long id) {
        maRepo.deleteById(id);
    }

    @DeleteMapping("/remover")
    public void removerPorObj (@RequestBody Marmiteria ma) {
        maRepo.delete(ma);
    }

    @PutMapping("/atualizar")
    public void atualizarMarmiteria(@RequestBody Marmiteria ma) {
        this.maRepo.save(ma);
    }

}
