package com.cadastro1.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Size(max = 15)
    @Column(nullable = false)
    private String telefone;

    @NotBlank
    @Email
    @Size(max = 50)
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String tipoProduto;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, unique = true)
    private String cnpj;
}
