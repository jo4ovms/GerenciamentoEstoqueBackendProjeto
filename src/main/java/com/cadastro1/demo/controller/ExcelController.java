package com.cadastro1.demo.controller;

import com.cadastro1.demo.service.ExcelService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @GetMapping("/produtos/export")
    public ResponseEntity<InputStreamResource> exportProdutosToExcel() throws IOException {
        ByteArrayInputStream in = excelService.exportProdutosToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=produtos.xlsx");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(new InputStreamResource(in));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @GetMapping("/fornecedores/export")
    public ResponseEntity<InputStreamResource> exportFornecedoresToExcel() throws IOException {
        ByteArrayInputStream in = excelService.exportFornecedoresToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=fornecedores.xlsx");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(new InputStreamResource(in));
    }
}