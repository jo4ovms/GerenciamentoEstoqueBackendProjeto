package com.cadastro1.demo.controller;

import com.cadastro1.demo.model.Fornecedor;
import com.cadastro1.demo.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Fornecedor> criarFornecedor(@RequestBody Fornecedor fornecedor) {
        try {
            Fornecedor novoFornecedor = fornecedorService.salvar(fornecedor);
            return ResponseEntity.ok(novoFornecedor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar fornecedor", e);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Fornecedor>> listarFornecedores() {
        try {
            List<Fornecedor> fornecedores = fornecedorService.listar();
            return ResponseEntity.ok(fornecedores);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar fornecedores", e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Fornecedor> buscarFornecedorPorId(@PathVariable Long id) {
        return fornecedorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedor) {
        try {
            Fornecedor fornecedorAtualizado = fornecedorService.atualizarFornecedor(id, fornecedor);
            return ResponseEntity.ok(fornecedorAtualizado);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar fornecedor", e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable Long id) {
        try {
            fornecedorService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao deletar fornecedor", e);
        }
    }
}
