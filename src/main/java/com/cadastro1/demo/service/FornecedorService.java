package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Fornecedor;
import com.cadastro1.demo.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private LogService logService;

    public Fornecedor salvar(Fornecedor fornecedor) {
        boolean isNew = (fornecedor.getId() == null);
        Fornecedor savedFornecedor = fornecedorRepository.save(fornecedor);
        if (isNew) {
            logService.logCreateFornecedor(savedFornecedor);
        } else {
            logService.logUpdateFornecedor(fornecedor, savedFornecedor);
        }
        return savedFornecedor;
    }

    public Fornecedor atualizarFornecedor(Long id, Fornecedor novoFornecedor) {
        Optional<Fornecedor> optionalOldFornecedor = fornecedorRepository.findById(id);
        if (optionalOldFornecedor.isPresent()) {
            Fornecedor oldFornecedor = optionalOldFornecedor.get();
            logService.logUpdateFornecedor(oldFornecedor, novoFornecedor);
            novoFornecedor.setId(id);
            return fornecedorRepository.save(novoFornecedor);
        } else {
            throw new RuntimeException("Fornecedor não encontrado");
        }
    }

    public List<Fornecedor> listar() {
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> buscarPorId(Long id) {
        return fornecedorRepository.findById(id);
    }

    public void deletar(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow();
        fornecedorRepository.deleteById(id);
        logService.logDeleteFornecedor(fornecedor);
    }
}
