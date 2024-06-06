package com.cadastro1.demo.repository;

import com.cadastro1.demo.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}