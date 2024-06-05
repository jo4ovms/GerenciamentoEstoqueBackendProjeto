package com.cadastro1.demo.controller;

import com.cadastro1.demo.model.Log;
import com.cadastro1.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Log>> listarLogs() {
        List<Log> logs = logService.listar();
        return ResponseEntity.ok(logs);
    }
}
