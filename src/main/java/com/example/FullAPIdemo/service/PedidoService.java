package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.dto.PedidoResponse;
import com.example.FullAPIdemo.model.entity.Pedido;
import com.example.FullAPIdemo.repository.PedidoRepository;
import com.example.FullAPIdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tools.jackson.databind.ObjectMapper;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pRepo;
    @Autowired
    private UserRepository uRepo;

    public ResponseEntity<List<PedidoResponse>> listUserChats(@RequestBody @Valid LoginRequest chatRequest){
        List<Pedido> list = pRepo.findByUserIdOrderByCreatedAtAsc(uRepo.findIdByUsername(chatRequest.getUsername()));
        List<PedidoResponse> responseList = new ArrayList<>();
        for (Pedido pedido : list) {
            System.out.println(pedido.getId());
            PedidoResponse pedidoResponse = new PedidoResponse(pedido);
            responseList.add(pedidoResponse);
        }
        ObjectMapper mapper = new ObjectMapper();
        String jsonList = mapper.writeValueAsString(responseList);

        System.out.println(jsonList);

        return ResponseEntity.ok().body(responseList);
    }
}
