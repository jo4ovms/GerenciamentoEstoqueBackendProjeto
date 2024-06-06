package com.cadastro1.demo.repository;

import com.cadastro1.demo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByQuantidadeLessThan(int quantidade);
    List<Produto> findByQuantidade(int quantidade);
    List<Produto> findByQuantidadeGreaterThanEqual(int quantidade);
}
