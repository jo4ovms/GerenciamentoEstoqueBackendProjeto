package com.cadastro1.demo.exceptions;

public class InsufficientStockException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InsufficientStockException(String message) {
        super(message);
    }
}