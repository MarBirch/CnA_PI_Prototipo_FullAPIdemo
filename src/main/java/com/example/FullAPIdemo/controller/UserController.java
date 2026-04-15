package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.CadastroRequest;
import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.model.entity.User;
import com.example.FullAPIdemo.repository.UserRepository;
import com.example.FullAPIdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/apiUser")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepo;

    //função de cadastro, não inclui variaveis alem de username e senha pq eu tive preguiça
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody @Valid CadastroRequest request) {
        return userService.cadastrar(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody @Valid LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/buscar/{id}")
    public Optional<User> buscarPorCodigo(@PathVariable(value = "id") Long id) {
        return userRepo.findById(id);
    }
}
//    //- buscar users por parte inicial do nome - FEITO
//    @GetMapping("/buscar/nome/{nome}")
//    public List<User> buscarPorParteNome(@PathVariable(value = "nome") String nome) {
//        return this.uRepo.findByParteNome(nome);
//    }
//
//    //- buscar users pela combinação de parte do nome e parte do e-mail - FEITO
//    @GetMapping("/buscarNomeEmail/{nome}/{email}")
//    public List<User> buscarPorNomeEmail(@PathVariable(value = "nome") String nome, @PathVariable(value = "email") String email) {
//        return this.uRepo.findByNomeEmail(nome,email);
//    }

//    //- remover user do banco de dados, ao informar o código deste - FEITO
//    @DeleteMapping("/remover/{codigo}")
//    public void removerPorCodigo(@PathVariable(value = "codigo") int codigo) {
//        uRepo.deleteById(codigo);
//    }
//
//    //- Remover user do banco de dados, ao informar o registro td - FEITO
//    @DeleteMapping("/remover")
//    public void removerPorObj (@RequestBody User u) {
//        uRepo.delete(u);
//    }
//
//    //- Atualizar user no banco de dados - FEITO
//    @PutMapping("/atualizar")
//    public void atualizarUser(@RequestBody User u) {
//        this.uRepo.save(u);
//    }

