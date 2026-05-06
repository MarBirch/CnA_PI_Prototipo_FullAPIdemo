package com.example.FullAPIdemo.controller;

import com.example.FullAPIdemo.model.dto.PedidoRequest;
import com.example.FullAPIdemo.model.dto.PedidoResponse;
import com.example.FullAPIdemo.model.entity.Cardapio;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.model.entity.Pedido;
import com.example.FullAPIdemo.model.entity.PedidoIngrediente;
import com.example.FullAPIdemo.repository.CardapioRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import com.example.FullAPIdemo.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apiPedido")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    PedidoRepository pRepo;

    @Autowired
    CardapioRepository caRepo;

    @Autowired
    MarmiteriaRepository maRepo;

    @GetMapping("/todos")
    public ResponseEntity<List<PedidoResponse>> buscarPorMarmiteria(@RequestParam Long marmiteriaId) {
        List<Pedido> pedidos = pRepo.findByMarmiteriaId(marmiteriaId);
        List<PedidoResponse> resp = pedidos.stream().map(PedidoResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/inserir")
    public ResponseEntity<?> inserirPedido(@RequestBody PedidoRequest req) {
        Optional<Cardapio> cardapioOpt = caRepo.findById(req.getCardapioId());
        if (cardapioOpt.isEmpty()) return ResponseEntity.badRequest().body("Cardápio não encontrado.");

        Cardapio cardapio = cardapioOpt.get();
        Marmiteria marmiteria = cardapio.getMarmiteria();

        Pedido pedido = new Pedido();
        pedido.setNomeCliente(req.getNomeCliente());
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

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        if (!pRepo.existsById(id)) return ResponseEntity.notFound().build();
        pRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}