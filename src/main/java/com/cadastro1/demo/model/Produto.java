package com.cadastro1.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private String fornecedor;


    @Override
    public String toString(){
        return nome;
    }
    // Getters and Setters
    // ...
}
