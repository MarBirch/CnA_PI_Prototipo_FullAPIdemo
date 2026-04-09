package com.example.FullAPIdemo.control;

import com.example.FullAPIdemo.model.entity.Chat;
import com.example.FullAPIdemo.model.entity.Pedido;
import com.example.FullAPIdemo.model.pojo.LoginUser;
import com.example.FullAPIdemo.repository.PedidoRepository;
import com.example.FullAPIdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/apiPedido")
public class PedidoController {
    @Autowired
    private PedidoRepository pRepo;
    @Autowired
    private UserRepository uRepo;

    @PostMapping("/pedidos")
    public ResponseEntity<String> listUserChats(@RequestBody @Valid LoginUser chatRequest){
        ArrayList<Pedido> list = pRepo.findByUserIdOrderByCreatedAtAsc(uRepo.findIdByUsername(chatRequest.getUsername()));

        ObjectMapper mapper = new ObjectMapper();
        String jsonList = mapper.writeValueAsString(list);

        System.out.println(jsonList);

        return ResponseEntity.ok().body(jsonList);
    }

}
