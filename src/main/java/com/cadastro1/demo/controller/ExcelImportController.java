package com.cadastro1.demo.controller;

import com.cadastro1.demo.service.ExcelImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/excel")
public class ExcelImportController {

    private final ExcelImportService excelImportService;

    public ExcelImportController(ExcelImportService excelImportService) {
        this.excelImportService = excelImportService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @PostMapping("/produtos/import")
    public ResponseEntity<String> importProdutosFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            excelImportService.importProdutosFromExcel(file);
            return ResponseEntity.ok("Produtos importados com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao importar produtos: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @PostMapping("/fornecedores/import")
    public ResponseEntity<String> importFornecedoresFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            excelImportService.importFornecedoresFromExcel(file);
            return ResponseEntity.ok("Fornecedores importados com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao importar fornecedores: " + e.getMessage());
        }
    }
}