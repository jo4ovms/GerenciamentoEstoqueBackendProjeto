package com.cadastro1.demo.service;

import com.cadastro1.demo.exceptions.InsufficientStockException;
import com.cadastro1.demo.exceptions.ResourceNotFoundException;
import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.model.SalesData;
import com.cadastro1.demo.model.Venda;
import com.cadastro1.demo.repository.ProdutoRepository;
import com.cadastro1.demo.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Venda registrarVenda(Venda venda) {
        Produto produto = produtoRepository.findById(venda.getProdutoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto not found for this id :: " + venda.getProdutoId()));

        if (produto.getQuantidade() < venda.getQuantidade()) {
            throw new InsufficientStockException("Insufficient stock for product :: " + produto.getNome());
        }

        produto.setQuantidade(produto.getQuantidade() - venda.getQuantidade());
        produtoRepository.save(produto);

        venda.setDataVenda(LocalDateTime.now());
        return vendaRepository.save(venda);
    }

    public List<SalesData> getSalesDataForMonth(int month) {
        List<Venda> vendas = vendaRepository.findAll();

        return vendas.stream()
                .filter(venda -> venda.getDataVenda().getMonthValue() == month)
                .collect(Collectors.groupingBy(venda -> venda.getDataVenda().getDayOfMonth()))
                .entrySet().stream()
                .map(entry -> new SalesData(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toList());
    }

    public List<Venda> listarVendasPorMes(int month) {
        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return vendaRepository.findAll().stream()
                .filter(venda -> !venda.getDataVenda().isBefore(startDate.atStartOfDay()) && !venda.getDataVenda().isAfter(endDate.atStartOfDay()))
                .collect(Collectors.toList());
    }
    }

