package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.GastosRequest;
import com.example.FullAPIdemo.model.entity.Gastos;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.GastosRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import com.example.FullAPIdemo.service.GastosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apiGastos")
@CrossOrigin(origins = "*")
public class GastosController {

    @Autowired
    GastosService gastosService;

    @PostMapping("/inserir")
    public ResponseEntity<?> inserirGastos(@RequestBody @Valid GastosRequest ga) {
        return gastosService.inserirGastos(ga);
    }

    @GetMapping("/filtrar")
    public List<Gastos> filtrar(
            @RequestParam Long marmiteriaId,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {return gastosService.filtrar(marmiteriaId, categoria, inicio, fim);
    }

    @GetMapping("/todos")
    public List<Gastos> buscarTodos(@RequestParam Long marmiteriaId){
        return gastosService.buscarTodos(marmiteriaId);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarGastos(@RequestBody Gastos ga) {
        return gastosService.atualizarGastos(ga);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        return gastosService.removerPorId(id);
    }
}