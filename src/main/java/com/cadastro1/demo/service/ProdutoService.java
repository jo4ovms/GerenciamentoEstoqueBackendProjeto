package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Log;
import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LogService logService;

    public Produto salvar(Produto produto) {
        boolean isNew = (produto.getId() == null);
        Produto savedProduto = produtoRepository.save(produto);
        String action = isNew ? "CREATE" : "UPDATE";
        String logMessage = isNew ? "Produto criado: " : "Produto atualizado: ";
        logService.salvar(new Log("Produto", action, logMessage + savedProduto.getNome(), LocalDateTime.now()));
        return savedProduto;
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        produtoRepository.deleteById(id);
        logService.salvar(new Log("Produto", "DELETE", "Produto deletado: " + produto.getNome(), LocalDateTime.now()));
    }

    public List<Produto> listarProdutosAbaixoDaQuantidadeSegura(int quantidadeSegura) {
        return produtoRepository.findByQuantidadeLessThan(quantidadeSegura);
    }

    public List<Produto> listarProdutosForaDeEstoque() {
        return produtoRepository.findByQuantidade(0);
    }

    public List<Produto> listarProdutosAdequados(int quantidadeSegura) {
        return produtoRepository.findByQuantidadeGreaterThanEqual(quantidadeSegura);
    }
}
