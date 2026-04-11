package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.dto.CadastroRequest;
import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.entity.User;
import com.example.FullAPIdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository uRepo;

    public ResponseEntity<Boolean> login(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getSenha());

        if (usernameExists(loginRequest.getUsername())){
            User u = this.uRepo.findByUsername(loginRequest.getUsername());
            if (u.getSenha().equals(loginRequest.getSenha())){
                System.out.println("sucesso");
                //retorna verdadeiro
                return ResponseEntity.ok().body(true);
            } else {
                //senha errada
                System.out.println("senha errada");
                return ResponseEntity.ok().body(false);
            }

        } else{
            //usuario n existe
            System.out.println("usuario n existe");
            return ResponseEntity.ok().body(false);
        }}

    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroRequest request) {
        System.out.println("request" + request.getUsername());
        if (usernameExists(request.getUsername())){
            System.out.println("username existe");
            return ResponseEntity.ok().body("username ja existe");
        } else{
                System.out.println("cadastrando...");
                System.out.println(request.getUsername());
                System.out.println(request.getSenha());
                //salvando usuario
                uRepo.save(new User(request.getUsername(), request.getSenha()));
                return ResponseEntity.ok().body("usuario cadastrado");}
    }

    public Boolean usernameExists(String username){
        List<User> userList = uRepo.findAll();
        for (User u : userList){
            System.out.println("pré if");
            if (u.getUsername() != null && u.getUsername().equals(username)){
                System.out.println("existe");
                return true;
            }
        }
        System.out.println("retorna falso");;
        return false;
    }

}
