package com.cadastro1.demo.controller;

import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/abaixo-da-quantidade-segura")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Produto>> listarProdutosAbaixoDaQuantidadeSegura() {
        try {
            List<Produto> produtos = produtoService.listarProdutosAbaixoDaQuantidadeSegura(5);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar produtos abaixo da quantidade segura", e);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoService.salvar(produto);
            return ResponseEntity.ok(novoProduto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar produto", e);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Produto>> listarProdutos() {
        try {
            List<Produto> produtos = produtoService.listar();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar produtos", e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar produto", e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao deletar produto", e);
        }
    }

    @GetMapping("/fora-de-estoque")
    public ResponseEntity<List<Produto>> listarProdutosForaDeEstoque() {
        try {
            List<Produto> produtos = produtoService.listarProdutosForaDeEstoque();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar produtos fora de estoque", e);
        }
    }

    @GetMapping("/adequados")
    public ResponseEntity<List<Produto>> listarProdutosAdequados() {
        try {
            List<Produto> produtos = produtoService.listarProdutosAdequados(5);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar produtos adequados", e);
        }
    }
}
