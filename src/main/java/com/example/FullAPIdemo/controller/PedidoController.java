package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.PedidoResponse;
import com.example.FullAPIdemo.model.entity.Pedido;
import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.repository.PedidoRepository;
import com.example.FullAPIdemo.repository.UserRepository;
import com.example.FullAPIdemo.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apiPedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;


    @PostMapping("/pedidos")
    public ResponseEntity<List<PedidoResponse>> listUserChats(@RequestBody @Valid LoginRequest chatRequest){
        return pedidoService.listUserChats(chatRequest);
    }

}
