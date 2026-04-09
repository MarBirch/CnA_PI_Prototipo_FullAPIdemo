package com.example.FullAPIdemo.control;


import com.example.FullAPIdemo.model.pojo.CadastroRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiCardapio")
public class CardapioController {
    @PostMapping("/inserir")
    public String inserir(@RequestBody CadastroRequest request){

    }
}
