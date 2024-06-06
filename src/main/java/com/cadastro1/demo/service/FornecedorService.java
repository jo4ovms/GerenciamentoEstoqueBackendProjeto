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
        boolean isNew = (fornecedor.getId() == null);
        Fornecedor savedFornecedor = fornecedorRepository.save(fornecedor);
        String action = isNew ? "CREATE" : "UPDATE";
        String logMessage = isNew ? "Fornecedor criado: " : "Fornecedor atualizado: ";
        logService.salvar(new Log("Fornecedor", action, logMessage + savedFornecedor.getNome(), LocalDateTime.now()));
        return savedFornecedor;
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
        logService.salvar(new Log("Fornecedor", "DELETE", "Fornecedor deletado: " + fornecedor.getNome(), LocalDateTime.now()));
    }
}
