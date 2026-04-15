package com.example.FullAPIdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Gastos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public Marmiteria getMarmiteria() {
        return marmiteria;
    }

    public void setMarmiteria(Marmiteria marmiteria) {
        this.marmiteria = marmiteria;
    }

    @Column
    private String nome;

    @Column
    private Double custo;

    @ManyToOne
    @JoinColumn(name = "marmiteria_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Marmiteria marmiteria;


}
