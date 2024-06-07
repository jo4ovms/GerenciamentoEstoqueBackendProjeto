package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (isNew) {
            logService.logCreateProduto(savedProduto);
        }
        return savedProduto;
    }

    public Produto atualizarProduto(Long id, Produto novoProduto) {
        Optional<Produto> optionalOldProduto = produtoRepository.findById(id);
        if (optionalOldProduto.isPresent()) {
            Produto oldProduto = optionalOldProduto.get();
            logService.logUpdateProduto(oldProduto, novoProduto);
            novoProduto.setId(id); // Ensure the ID is set for the update
            return produtoRepository.save(novoProduto);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
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
        logService.logDeleteProduto(produto);
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
