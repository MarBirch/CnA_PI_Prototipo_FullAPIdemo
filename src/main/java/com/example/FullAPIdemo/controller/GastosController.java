package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.GastosRequest;
import com.example.FullAPIdemo.model.entity.Gastos;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.GastosRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apiGastos")
@CrossOrigin(origins = "*")
public class GastosController {

    @Autowired
    GastosRepository gaRepo;

    @Autowired
    MarmiteriaRepository marmiteriaRepo;

    @PostMapping("/inserir")
    public ResponseEntity<?> inserirGastos(@RequestBody @Valid GastosRequest ga) {
        Long marmiteriaId = ga.getMarmiteriaId();
        System.out.println(marmiteriaId.toString());
        Marmiteria marmiteria = marmiteriaRepo.getReferenceById(marmiteriaId);
//        if (marmiteria.isEmpty()) {
//            return ResponseEntity.badRequest()
//                    .body("Marmiteria com id " + marmiteriaId + " não encontrada.");
//        }
        Gastos gastos = new Gastos(ga.getCusto(), ga.getCategoria(), ga.getData(), ga.getObservacao(),marmiteria);

        //ga.setMarmiteria(marmiteria);
        return ResponseEntity.status(201).body(gaRepo.save(gastos));
    }

    @GetMapping("/todos")
    public List<Gastos> buscarTodos(@RequestParam Long marmiteriaId) {
        return gaRepo.findByMarmiteriaId(marmiteriaId);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Gastos> buscarPorId(@PathVariable Long id) {
        return gaRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filtrar")
    public List<Gastos> filtrar(
            @RequestParam Long marmiteriaId,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        if (categoria != null && inicio != null && fim != null) {
            return gaRepo.findByMarmiteriaIdAndCategoriaIgnoreCaseAndDataBetween(
                    marmiteriaId, categoria, inicio, fim);
        } else if (categoria != null) {
            return gaRepo.findByMarmiteriaIdAndCategoriaIgnoreCase(marmiteriaId, categoria);
        } else if (inicio != null && fim != null) {
            return gaRepo.findByMarmiteriaIdAndDataBetween(marmiteriaId, inicio, fim);
        }
        return gaRepo.findByMarmiteriaId(marmiteriaId);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarGastos(@RequestBody Gastos ga) {
        if (ga.getId() == null || !gaRepo.existsById(ga.getId())) {
            return ResponseEntity.notFound().build();
        }

        // preserva a marmiteria original se não vier no body
        Gastos existente = gaRepo.findById(ga.getId()).get();
        if (ga.getMarmiteria() == null) {
            ga.setMarmiteria(existente.getMarmiteria());
        }

        return ResponseEntity.ok(gaRepo.save(ga));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        if (!gaRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}