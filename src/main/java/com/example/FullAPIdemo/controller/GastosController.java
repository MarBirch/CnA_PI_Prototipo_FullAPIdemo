package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.entity.Gastos;
import com.example.FullAPIdemo.repository.GastosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/apiGastos")
public class GastosController {
    @Autowired
    GastosRepository gaRepo;

    @PostMapping("/inserir")
    public void inserirGastos(@RequestBody Gastos ga) {
        gaRepo.save(ga);
    }

    @GetMapping("/todos")
    public List<Gastos> buscarTodosGastoss() {
        return gaRepo.findAll();
    }

    @GetMapping("/busgar/{codigo}")
    public Optional<Gastos> buscarPorCodigo(@PathVariable(value = "id") Long id) {
        return gaRepo.findById(id);
    }

    @DeleteMapping("/remover/{id}")
    public void removerPorCodigo(@PathVariable(value = "id") Long id) {
        gaRepo.deleteById(id);
    }

    @DeleteMapping("/remover")
    public void removerPorObj (@RequestBody Gastos ga) {
        gaRepo.delete(ga);
    }

    @PutMapping("/atualizar")
    public void atualizarGastos(@RequestBody Gastos ga) {
        this.gaRepo.save(ga);
    }

}
