package com.cadastro1.demo.model;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String entity;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
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

    // Getters and setters
}
