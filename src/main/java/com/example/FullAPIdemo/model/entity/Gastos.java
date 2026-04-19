package com.example.FullAPIdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Gastos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double custo;

    @Column
    private String categoria;

    @Column
    private LocalDate data;

    @Column(length = 500)
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "marmiteria_id", nullable = false)
    @JsonIgnore
    private Marmiteria marmiteria;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getCusto() { return custo; }
    public void setCusto(Double custo) { this.custo = custo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Marmiteria getMarmiteria() { return marmiteria; }
    public void setMarmiteria(Marmiteria marmiteria) { this.marmiteria = marmiteria; }
}