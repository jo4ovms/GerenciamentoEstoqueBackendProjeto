package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Log;
import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.model.SalesData;
import com.cadastro1.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LogService logService;


    public Produto salvar(Produto produto) {
        Produto savedProduto = produtoRepository.save(produto);
        logService.salvar(new Log("Produto", "CREATE", "Produto criado: " + savedProduto.toString(), LocalDateTime.now()));
        return produtoRepository.save(produto);
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizar(Long id, Produto produto) {
        Produto updatedProduto = produtoRepository.save(produto);
        logService.salvar(new Log("Produto", "UPDATE", "Produto atualizado: " + updatedProduto.toString(), LocalDateTime.now()));
        return updatedProduto;
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        produtoRepository.deleteById(id);
        logService.salvar(new Log("Produto", "DELETE", "Produto deletado: " + produto.toString(), LocalDateTime.now()));
    }
    public List<Produto> listarProdutosAbaixoDaQuantidadeSegura(int quantidadeSegura) {
        return produtoRepository.findByQuantidadeLessThan(quantidadeSegura);
    }

    public List<SalesData> getSalesData(int month) {
        // Implementar a lógica para buscar os dados de vendas com base no mês
        List<SalesData> salesData = new ArrayList<>();
        // Adicionar dados de exemplo
        salesData.add(new SalesData("01/03", 10));
        salesData.add(new SalesData("02/03", 15));
        // ...
        return salesData;
    }

}
