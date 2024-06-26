package com.cadastro1.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String entity;

    @NotBlank
    @Column(nullable = false)
    private String action;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Log() {
    }

    public Log(String entity, String action, String details, LocalDateTime timestamp) {
        this.entity = entity;
        this.action = action;
        this.details = details;
        this.timestamp = timestamp;
    }
}
