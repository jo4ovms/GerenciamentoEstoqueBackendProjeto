package com.cadastro1.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String nome;

    @NotNull
    @Column(nullable = false)
    private Double valor;

    @NotNull
    @Column(nullable = false)
    private Integer quantidade;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String fornecedor;

    @Override
    public String toString(){
        return nome;
    }
}
