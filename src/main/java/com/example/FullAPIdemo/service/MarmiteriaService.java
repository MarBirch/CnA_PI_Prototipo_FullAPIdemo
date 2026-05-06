package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class MarmiteriaService {
    @Autowired
    MarmiteriaRepository maRepo;

    public void inserirMarmiteria(@RequestBody Marmiteria ma) {
        maRepo.save(ma);
    }

    public List<Marmiteria> buscarTodosMarmiterias() {
        return maRepo.findAll();
    }

    public Marmiteria buscarPorCodigo(@PathVariable(value = "id") Long id) {
        return maRepo.getById(id);
    }

    public void removerPorCodigo(@PathVariable(value = "id") Long id) {
        maRepo.deleteById(id);
    }

    public void removerPorObj (@RequestBody Marmiteria ma) {
        maRepo.delete(ma);
    }

    public void atualizarMarmiteria(@RequestBody Marmiteria ma) {
        this.maRepo.save(ma);
    }
}
