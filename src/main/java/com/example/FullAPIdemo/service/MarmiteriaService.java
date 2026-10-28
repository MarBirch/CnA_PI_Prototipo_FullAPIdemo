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

    @Autowired
    com.example.FullAPIdemo.security.PasswordService passwords;

    /** Valida credenciais (BCrypt, com migracao de senhas legadas). */
    public Optional<Marmiteria> autenticar(String email, String senha) {
        if (email == null || senha == null) return Optional.empty();
        Optional<Marmiteria> opt = maRepo.findFirstByEmailIgnoreCase(email.trim());
        if (opt.isEmpty() || !passwords.matches(senha, opt.get().getSenha())) return Optional.empty();
        Marmiteria m = opt.get();
        if (passwords.precisaMigrar(m.getSenha())) {
            m.setSenha(passwords.hash(senha));
            maRepo.save(m);
        }
        return Optional.of(m);
    }

    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<Marmiteria> opt = autenticar(req.getUsername(), req.getSenha());
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos.");
        }
        Marmiteria m = opt.get();
        if (!m.isAcessoAtivo()) {
            return ResponseEntity.status(403).body("Acesso da empresa não está ativo.");
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("id", m.getId());
        resp.put("nome", m.getNome());
        return ResponseEntity.ok(resp);
    }

    public void inserirMarmiteria(@RequestBody Marmiteria ma) {
        ma.setId(null);
        if (ma.getSenha() != null && !ma.getSenha().isBlank()) ma.setSenha(passwords.hash(ma.getSenha()));
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
            if (!passwords.matches(ma.getSenhaAtual(), existente.getSenha())) {
                return ResponseEntity.status(401).body("Senha atual incorreta.");
            }
            existente.setSenha(passwords.hash(ma.getSenha()));
        }

        return ResponseEntity.ok(maRepo.save(existente));
    }

    public ResponseEntity<?> alterarStatus(Long id, Boolean aberto) {
        Optional<Marmiteria> opt = maRepo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Marmiteria m = opt.get();
        m.setStatus(aberto);
        maRepo.save(m);
        return ResponseEntity.ok().build();
    }

}
