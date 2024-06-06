package com.cadastro1.demo.controller;

import com.cadastro1.demo.model.Venda;
import com.cadastro1.demo.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<Venda> registrarVenda(@RequestBody Venda venda) {
        Venda novaVenda = vendaService.registrarVenda(venda);
        return ResponseEntity.ok(novaVenda);
    }

    @GetMapping("/sales-data")
    public ResponseEntity<List<Venda>> getSalesData(@RequestParam int month) {
        List<Venda> vendas = vendaService.listarVendasPorMes(month);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/itens-mais-vendidos")
    public ResponseEntity<List<VendaService.ProdutoQuantidade>> getItensMaisVendidos() {
        List<VendaService.ProdutoQuantidade> itensMaisVendidos = vendaService.listarItensMaisVendidos();
        return ResponseEntity.ok(itensMaisVendidos);
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaService.listarVendas();
        return ResponseEntity.ok(vendas);
    }

}
