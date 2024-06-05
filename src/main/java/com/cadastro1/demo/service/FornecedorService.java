package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Fornecedor;
import com.cadastro1.demo.model.Log;
import com.cadastro1.demo.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;


    @Autowired
    private LogService logService;

    public Fornecedor salvar(Fornecedor fornecedor) {
        Fornecedor savedFornecedor = fornecedorRepository.save(fornecedor);
        logService.salvar(new Log("Fornecedor", "CREATE", "Fornecedor criado: " + savedFornecedor.getNome(), LocalDateTime.now()));
        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizar(Long id, Fornecedor fornecedor) {
        Fornecedor updatedFornecedor = fornecedorRepository.save(fornecedor);
        logService.salvar(new Log("Fornecedor", "UPDATE", "Fornecedor atualizado: " + updatedFornecedor.getNome(), LocalDateTime.now()));
        return updatedFornecedor;
    }

    public List<Fornecedor> listar() {
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> buscarPorId(Long id) {
        return fornecedorRepository.findById(id);
    }

    public void deletar(Long id) {
        fornecedorRepository.deleteById(id);
        logService.salvar(new Log("Fornecedor", "DELETE", "Fornecedor deletado com id: " + id, LocalDateTime.now()));
    }
}