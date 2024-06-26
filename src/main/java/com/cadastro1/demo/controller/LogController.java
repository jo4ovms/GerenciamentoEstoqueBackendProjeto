package com.cadastro1.demo.controller;

import com.cadastro1.demo.model.Log;
import com.cadastro1.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Log>> listarLogs() {
        try {
            List<Log> logs = logService.listar();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar logs", e);
        }
    }

    @GetMapping("/por-data")
    public ResponseEntity<List<Log>> listarLogsPorData(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
            LocalDateTime end = LocalDate.parse(endDate).atTime(23, 59, 59);
            List<Log> logs = logService.listarLogsPorData(start, end);
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao listar logs por data", e);
        }
    }
}
