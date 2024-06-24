package com.cadastro1.demo.service;

import com.cadastro1.demo.exceptions.InsufficientStockException;
import com.cadastro1.demo.exceptions.ResourceNotFoundException;
import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.model.Venda;
import com.cadastro1.demo.repository.ProdutoRepository;
import com.cadastro1.demo.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LogService logService;

    public Venda registrarVenda(Venda venda) {
        Produto produto = produtoRepository.findById(venda.getProdutoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto not found for this id :: " + venda.getProdutoId()));

        if (produto.getQuantidade() < venda.getQuantidade()) {
            throw new InsufficientStockException("Insufficient stock for product :: " + produto.getNome());
        }

        produto.setQuantidade(produto.getQuantidade() - venda.getQuantidade());
        produtoRepository.save(produto);

        venda.setDataVenda(LocalDateTime.now());
        Venda novaVenda = vendaRepository.save(venda);
        logService.logCreateVenda(novaVenda);
        return novaVenda;
    }


    public List<Venda> listarVendasPorMes(int month) {
        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return vendaRepository.findAll().stream()
                .filter(venda -> !venda.getDataVenda().isBefore(startDate.atStartOfDay()) && !venda.getDataVenda().isAfter(endDate.atStartOfDay()))
                .collect(Collectors.toList());
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    public List<Venda> listarVendasPorData(LocalDateTime startDate, LocalDateTime endDate) {
        return vendaRepository.findAll().stream()
                .filter(venda -> !venda.getDataVenda().isBefore(startDate) && !venda.getDataVenda().isAfter(endDate))
                .collect(Collectors.toList());
    }

    public List<ProdutoQuantidade> listarItensMaisVendidos() {
        Map<Long, Integer> vendasAgrupadas = vendaRepository.findAll().stream()
                .collect(Collectors.groupingBy(Venda::getProdutoId, Collectors.summingInt(Venda::getQuantidade)));

        return vendasAgrupadas.entrySet().stream()
                .map(entry -> {
                    Produto produto = produtoRepository.findById(entry.getKey()).orElse(null);
                    if (produto != null) {
                        return new ProdutoQuantidade(produto.getNome(), entry.getValue());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted((pq1, pq2) -> Integer.compare(pq2.getQuantidade(), pq1.getQuantidade()))
                .collect(Collectors.toList());
    }

    public static class ProdutoQuantidade {
        private String produto;
        private int quantidade;

        public ProdutoQuantidade(String produto, int quantidade) {
            this.produto = produto;
            this.quantidade = quantidade;
        }

        // Getters e Setters
        public String getProduto() {
            return produto;
        }

        public void setProduto(String produto) {
            this.produto = produto;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }
}
