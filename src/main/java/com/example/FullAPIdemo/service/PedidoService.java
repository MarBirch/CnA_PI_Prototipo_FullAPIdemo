package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.StatusPedido;
import com.example.FullAPIdemo.model.dto.LoginRequest;
import com.example.FullAPIdemo.model.dto.PedidoRequest;
import com.example.FullAPIdemo.model.dto.PedidoResponse;
import com.example.FullAPIdemo.model.entity.*;
import com.example.FullAPIdemo.repository.CardapioRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import com.example.FullAPIdemo.repository.PedidoIngredienteRepository;
import com.example.FullAPIdemo.repository.PedidoRepository;
import com.example.FullAPIdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pRepo;

    @Autowired
    CardapioRepository caRepo;

    @Autowired
    UserRepository uRepo;

    @Autowired
    MarmiteriaRepository maRepo;

    @Autowired
    PedidoIngredienteRepository piRepo;

    public ResponseEntity<List<PedidoResponse>> buscarPorMarmiteria(@RequestParam Long marmiteriaId) {
        List<Pedido> pedidos = pRepo.findByMarmiteriaId(marmiteriaId);
        List<PedidoResponse> resp = pedidos.stream().map(PedidoResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    public ResponseEntity<List<PedidoResponse>> listUserChats(@RequestBody @Valid LoginRequest chatRequest) {
        List<Pedido> list = pRepo.findByUserIdOrderByCreatedAtAsc(uRepo.findIdByUsername(chatRequest.getUsername()));
        List<PedidoResponse> responseList = new ArrayList<>();
        for (Pedido pedido : list) {
            System.out.println(pedido.getId());
            responseList.add(new PedidoResponse(pedido));
        }
        ObjectMapper mapper = new ObjectMapper();
        String jsonList = mapper.writeValueAsString(responseList);
        System.out.println(jsonList);
        return ResponseEntity.ok().body(responseList);
    }

    public ResponseEntity<?> inserirPedido(@RequestBody PedidoRequest req) {
        Optional<Cardapio> cardapioOpt = caRepo.findById(req.getCardapioId());
        if (cardapioOpt.isEmpty()) return ResponseEntity.badRequest().body("Cardápio não encontrado.");

        Cardapio cardapio = cardapioOpt.get();
        Marmiteria marmiteria = cardapio.getMarmiteria();

        Pedido pedido = new Pedido();

        Long userId = uRepo.findIdByUsername(req.getUsername());
        User user = uRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado: " + req.getUserId()));
        pedido.setUser(user);
        pedido.setNomeCliente(null);
        pedido.setCardapio(cardapio);
        pedido.setMarmiteria(marmiteria);
        pedido.setValor(BigDecimal.ZERO);

        List<PedidoIngrediente> ingredientes = req.getIngredientes().stream().map(i -> {
            PedidoIngrediente pi = new PedidoIngrediente();
            pi.setNome(i.getNome());
            pi.setValorPorGramas(i.getValorPorGramas());
            pi.setGramas(i.getGramas());
            pi.setPosicao(i.getPosicao());
            pi.setPedido(pedido);
            return pi;
        }).collect(Collectors.toList());

        pedido.setIngredientes(ingredientes);

        BigDecimal total = ingredientes.stream()
                .map(i -> i.getValorPorGramas().multiply(i.getGramas()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValor(total);

        return ResponseEntity.status(201).body(new PedidoResponse(pRepo.save(pedido)));
    }

    /**
     * Atualiza as gramas dos ingredientes de um pedido PENDENTE ou CONFIRMADO.
     * Ingredientes que não aparecerem no body são removidos; novos são inseridos.
     */
    public ResponseEntity<?> atualizarIngredientes(
            @PathVariable Long id,
            @RequestBody PedidoRequest req) {

        Optional<Pedido> opt = pRepo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Pedido pedido = opt.get();

        String status = pedido.getStatus().name();
        if (!status.equals("PENDENTE")) {
            return ResponseEntity.badRequest()
                    .body("Só é possível editar pedidos com status PENDENTE.");
        }

//        System.out.printf();
        piRepo.deleteByPedidoId(id);
        pedido.getIngredientes().clear();

        List<PedidoIngrediente> novos = req.getIngredientes().stream().map(i -> {
            PedidoIngrediente pi = new PedidoIngrediente();
            pi.setNome(i.getNome());
            pi.setValorPorGramas(i.getValorPorGramas());
            pi.setGramas(i.getGramas());
            pi.setPosicao(i.getPosicao());
            pi.setPedido(pedido);
            return pi;
        }).collect(Collectors.toList());

        pedido.setIngredientes(novos);


        BigDecimal total = novos.stream()
                .map(i -> i.getValorPorGramas().multiply(i.getGramas()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValor(total);

        return ResponseEntity.ok(new PedidoResponse(pRepo.save(pedido)));
    }

    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        if (!pRepo.existsById(id)) return ResponseEntity.notFound().build();
        pRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> atualizarStatus(Long id, String status) {
        Optional<Pedido> opt = pRepo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        try {
            StatusPedido novoStatus = StatusPedido.valueOf(status);
            Pedido pedido = opt.get();
            pedido.setStatus(novoStatus);
            return ResponseEntity.ok(new PedidoResponse(pRepo.save(pedido)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Status inválido: " + status);
        }
    }
}