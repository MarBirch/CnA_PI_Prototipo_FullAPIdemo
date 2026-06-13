package com.example.FullAPIdemo.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class PedidoRequest {

    private Long userId;
    private String username;
    private Long cardapioId;
    private List<IngredienteItem> ingredientes;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCardapioId() { return cardapioId; }
    public void setCardapioId(Long cardapioId) { this.cardapioId = cardapioId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<IngredienteItem> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<IngredienteItem> ingredientes) { this.ingredientes = ingredientes; }

    public static class IngredienteItem {
        private String nome;
        private BigDecimal valorPorGramas;
        private BigDecimal gramas;
        private Integer posicao;

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public BigDecimal getValorPorGramas() { return valorPorGramas; }
        public void setValorPorGramas(BigDecimal v) { this.valorPorGramas = v; }

        public BigDecimal getGramas() { return gramas; }
        public void setGramas(BigDecimal gramas) { this.gramas = gramas; }

        public Integer getPosicao() { return posicao; }
        public void setPosicao(Integer posicao) { this.posicao = posicao; }
    }
}