package com.example.FullAPIdemo.control;

import com.example.FullAPIdemo.model.pojo.CadastroRequest;
import com.example.FullAPIdemo.model.pojo.LoginUser;
import com.example.FullAPIdemo.repository.UserRepository;
import com.example.FullAPIdemo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/apiUser")
public class UserController {
    @Autowired
    UserRepository uRepo;

//    //- cadastrar users - FEITO
//    @PostMapping("/inserir")
//    public void inserirUser(@RequestBody User u) {
//        uRepo.save(u);
//    }
//
//    //- buscar todos os users - FEITO
//    @GetMapping("/todos")
//    public List<User> buscarTodosUsers() {
//        return uRepo.findAll();
//    }
//
//    //- buscar users por código - FEITO
//    @GetMapping("/buscar/{codigo}")
//    public Optional<User> buscarPorCodigo(@PathVariable(value = "codigo") int codigo) {
//        return uRepo.findById(codigo);
//    }

//    //- buscar users pelo e-mail - FEITO
    @PostMapping("/cadastro")
    public String cadastrar(@RequestBody CadastroRequest request) {
        System.out.println(request.getUsername());
        List<User> userList = this.uRepo.findByUsernameIs(request.getUsername());
        try{
            System.out.println(userList.getFirst().getUsername());
            System.out.println(userList.getFirst().getPassword());
            User nullTest = userList.getFirst();
            if (nullTest.getUsername().equals(request.getUsername())){
                System.out.println("username");
                return "username already exists";
            } else{
                return "error";
            }
        }catch (NoSuchElementException e){
            System.out.println("execpotopn");
            System.out.println(request.getUsername());
            System.out.println(request.getPassword());
            uRepo.save(new User(request.getUsername(),request.getPassword()));
            return "success";
        }
    }

    @PostMapping("/login")
    public boolean Login(@RequestBody LoginUser loginUser) {
        System.out.println(loginUser.getUsername());
        System.out.println(loginUser.getPassword());
        List<User> userList = this.uRepo.findByUsernameIs(loginUser.getUsername());
        try{
            System.out.println(userList.getFirst().getUsername());
            System.out.println(userList.getFirst().getPassword());
            User u = userList.getFirst();
            if (u.getPassword().equals(loginUser.getPassword())){
                System.out.println("logged");
                return true;
            }else{
                userList = null;
                System.out.println("wrong password");
                return false;
            }
        }
        catch (NoSuchElementException e){
            System.out.println("null");
            return false;
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
}
