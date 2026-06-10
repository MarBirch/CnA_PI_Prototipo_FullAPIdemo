package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.dto.PedidoRequest;
import com.example.FullAPIdemo.model.dto.PedidoResponse;
import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.model.entity.Pedido;
import com.example.FullAPIdemo.model.entity.PedidoIngrediente;
import com.example.FullAPIdemo.repository.CardapioRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import com.example.FullAPIdemo.repository.PedidoRepository;
import com.example.FullAPIdemo.service.CardapioService;
import com.example.FullAPIdemo.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apiPedido")
@CrossOrigin(origins = "*")
public class PedidoController {
    @Autowired
    PedidoService pedidoService;

    @GetMapping("/todos")
    public ResponseEntity<List<PedidoResponse>> buscarPorMarmiteria(@RequestParam Long marmiteriaId) {
        return pedidoService.buscarPorMarmiteria(marmiteriaId);
    }

    @PostMapping("/pedidos")
    public ResponseEntity<List<PedidoResponse>> listUserChats(@RequestBody @Valid LoginRequest chatRequest){
        return pedidoService.listUserChats(chatRequest);
    }

    @PostMapping("/inserir")
    public ResponseEntity<?> inserirPedido(@RequestBody PedidoRequest req) {return pedidoService.inserirPedido(req);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        return pedidoService.removerPorId(id);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarIngredientes(
            @PathVariable Long id,
            @RequestBody PedidoRequest req) {
        return pedidoService.atualizarIngredientes(id, req);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
        return pedidoService.atualizarStatus(id, status);
    }
}