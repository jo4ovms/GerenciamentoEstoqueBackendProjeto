package com.cadastro1.demo.controller;

import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.model.SalesData;
import com.cadastro1.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/abaixo-da-quantidade-segura")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Produto>> listarProdutosAbaixoDaQuantidadeSegura() {
        List<Produto> produtos = produtoService.listarProdutosAbaixoDaQuantidadeSegura(5);
        return ResponseEntity.ok(produtos);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.salvar(produto);
        return ResponseEntity.ok(novoProduto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listar();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        if (!produtoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        produto.setId(id);
        Produto produtoAtualizado = produtoService.salvar(produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        if (!produtoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sales-data")
    public ResponseEntity<List<SalesData>> getSalesData(@RequestParam int month) {
        List<SalesData> salesData = produtoService.getSalesData(month);
        return ResponseEntity.ok(salesData);
    }


    @GetMapping("/fora-de-estoque")
    public ResponseEntity<List<Produto>> listarProdutosForaDeEstoque() {
        List<Produto> produtos = produtoService.listarProdutosForaDeEstoque();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/adequados")
    public ResponseEntity<List<Produto>> listarProdutosAdequados() {
        List<Produto> produtos = produtoService.listarProdutosAdequados(5);
        return ResponseEntity.ok(produtos);
    }


}
