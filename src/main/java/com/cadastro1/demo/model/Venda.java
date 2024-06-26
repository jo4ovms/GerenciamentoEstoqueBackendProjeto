package com.cadastro1.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long produtoId;

    @NotNull
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataVenda;


}
