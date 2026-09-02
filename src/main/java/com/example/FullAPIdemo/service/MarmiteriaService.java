package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MarmiteriaService {
    @Autowired
    MarmiteriaRepository maRepo;

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

    public List<Marmiteria> buscarMarmiteriasAbertas() {
        return maRepo.findAll();
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        return maRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> atualizar(Marmiteria ma) {
        if (!maRepo.existsById(ma.getId())) return ResponseEntity.notFound().build();
        Marmiteria existente = maRepo.findById(ma.getId()).get();
        existente.setNome(ma.getNome());
        existente.setEmail(ma.getEmail());
        existente.setTelefone(ma.getTelefone());
        existente.setCnpj(ma.getCnpj());
        existente.setCep(ma.getCep());
        existente.setNumero(ma.getNumero());
        existente.setRua(ma.getRua());
        existente.setBairro(ma.getBairro());
        existente.setCidade(ma.getCidade());
        existente.setUf(ma.getUf());

        if (ma.getSenha() != null && !ma.getSenha().isBlank()) {
            if (ma.getSenhaAtual() == null || !ma.getSenhaAtual().equals(existente.getSenha())) {
                return ResponseEntity.status(401).body("Senha atual incorreta.");
            }
            existente.setSenha(ma.getSenha());
        }

        return ResponseEntity.ok(maRepo.save(existente));
    }

}
