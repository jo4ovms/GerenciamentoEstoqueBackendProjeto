package com.cadastro1.demo.controller;

import com.cadastro1.demo.model.Venda;
import com.cadastro1.demo.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<Venda> registrarVenda(@RequestBody Venda venda) {
        try {
            Venda novaVenda = vendaService.registrarVenda(venda);
            return ResponseEntity.ok(novaVenda);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao registrar venda", e);
        }
    }

    @GetMapping("/sales-data")
    public ResponseEntity<List<Venda>> getSalesData(@RequestParam int month) {
        try {
            List<Venda> vendas = vendaService.listarVendasPorMes(month);
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter dados de vendas", e);
        }
    }

    @GetMapping("/itens-mais-vendidos")
    public ResponseEntity<List<VendaService.ProdutoQuantidade>> getItensMaisVendidos() {
        try {
            List<VendaService.ProdutoQuantidade> itensMaisVendidos = vendaService.listarItensMaisVendidos();
            return ResponseEntity.ok(itensMaisVendidos);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter itens mais vendidos", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        try {
            List<Venda> vendas = vendaService.listarVendas();
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar vendas", e);
        }
    }
}
