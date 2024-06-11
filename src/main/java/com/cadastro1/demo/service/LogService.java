package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Fornecedor;
import com.cadastro1.demo.model.Log;
import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.model.Venda;
import com.cadastro1.demo.repository.LogRepository;
import com.cadastro1.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Log salvar(Log log) {
        return logRepository.save(log);
    }

    public List<Log> listar() {
        return logRepository.findAll();
    }

    public List<Log> listarLogsPorData(LocalDateTime startDate, LocalDateTime endDate) {
        return logRepository.findAll().stream()
                .filter(log -> !log.getTimestamp().isBefore(startDate) && !log.getTimestamp().isAfter(endDate))
                .collect(Collectors.toList());
    }

    // Produto Logs
    public void logUpdateProduto(Produto oldProduto, Produto newProduto) {
        StringBuilder details = new StringBuilder();
        details.append(String.format("Produto: %s;", oldProduto.getNome()));

        boolean changesDetected = false;

        if (!oldProduto.getNome().equals(newProduto.getNome())) {
            details.append(String.format(" Nome alterado de '%s' para '%s';", oldProduto.getNome(), newProduto.getNome()));
            changesDetected = true;
        }
        if (!oldProduto.getValor().equals(newProduto.getValor())) {
            details.append(String.format(" Valor alterado de '%s' para '%s';", oldProduto.getValor(), newProduto.getValor()));
            changesDetected = true;
        }
        if (!oldProduto.getQuantidade().equals(newProduto.getQuantidade())) {
            details.append(String.format(" Quantidade alterada de '%s' para '%s';", oldProduto.getQuantidade(), newProduto.getQuantidade()));
            changesDetected = true;
        }
        if (!oldProduto.getFornecedor().equals(newProduto.getFornecedor())) {
            details.append(String.format(" Fornecedor alterado de '%s' para '%s';", oldProduto.getFornecedor(), newProduto.getFornecedor()));
            changesDetected = true;
        }

        if (!changesDetected) {
            details.append(" Sem alterações detectadas;");
        }

        logRepository.save(new Log("Produto: " + oldProduto.getNome(), "UPDATE", details.toString(), LocalDateTime.now()));
    }

    public void logCreateProduto(Produto produto) {
        String details = String.format("Produto criado: %s; Valor: %s; Quantidade: %s; Fornecedor: %s;",
                produto.getNome(), produto.getValor(), produto.getQuantidade(), produto.getFornecedor());
        logRepository.save(new Log("Produto: " + produto.getNome(), "CREATE", details, LocalDateTime.now()));
    }

    public void logDeleteProduto(Produto produto) {
        String details = String.format("Produto deletado: %s; Valor: %s; Quantidade: %s; Fornecedor: %s;",
                produto.getNome(), produto.getValor(), produto.getQuantidade(), produto.getFornecedor());
        logRepository.save(new Log("Produto: " + produto.getNome(), "DELETE", details, LocalDateTime.now()));
    }

    // Fornecedor Logs
    public void logUpdateFornecedor(Fornecedor oldFornecedor, Fornecedor newFornecedor) {
        StringBuilder details = new StringBuilder();
        details.append(String.format("Fornecedor: %s;", oldFornecedor.getNome()));

        boolean changesDetected = false;

        if (!oldFornecedor.getNome().equals(newFornecedor.getNome())) {
            details.append(String.format(" Nome alterado de '%s' para '%s';", oldFornecedor.getNome(), newFornecedor.getNome()));
            changesDetected = true;
        }
        if (!oldFornecedor.getTelefone().equals(newFornecedor.getTelefone())) {
            details.append(String.format(" Telefone alterado de '%s' para '%s';", oldFornecedor.getTelefone(), newFornecedor.getTelefone()));
            changesDetected = true;
        }
        if (!oldFornecedor.getEmail().equals(newFornecedor.getEmail())) {
            details.append(String.format(" Email alterado de '%s' para '%s';", oldFornecedor.getEmail(), newFornecedor.getEmail()));
            changesDetected = true;
        }
        if (!oldFornecedor.getTipoProduto().equals(newFornecedor.getTipoProduto())) {
            details.append(String.format(" TipoProduto alterado de '%s' para '%s';", oldFornecedor.getTipoProduto(), newFornecedor.getTipoProduto()));
            changesDetected = true;
        }
        if (!oldFornecedor.getCnpj().equals(newFornecedor.getCnpj())) {
            details.append(String.format(" CNPJ alterado de '%s' para '%s';", oldFornecedor.getCnpj(), newFornecedor.getCnpj()));
            changesDetected = true;
        }

        if (!changesDetected) {
            details.append(" Sem alterações detectadas;");
        }

        logRepository.save(new Log("Fornecedor: " + oldFornecedor.getNome(), "UPDATE", details.toString(), LocalDateTime.now()));
    }

    public void logCreateFornecedor(Fornecedor fornecedor) {
        String details = String.format("Fornecedor criado: %s; Telefone: %s; Email: %s; TipoProduto: %s; CNPJ: %s;",
                fornecedor.getNome(), fornecedor.getTelefone(), fornecedor.getEmail(), fornecedor.getTipoProduto(), fornecedor.getCnpj());
        logRepository.save(new Log("Fornecedor: " + fornecedor.getNome(), "CREATE", details, LocalDateTime.now()));
    }

    public void logDeleteFornecedor(Fornecedor fornecedor) {
        String details = String.format("Fornecedor deletado: %s; Telefone: %s; Email: %s; TipoProduto: %s; CNPJ: %s;",
                fornecedor.getNome(), fornecedor.getTelefone(), fornecedor.getEmail(), fornecedor.getTipoProduto(), fornecedor.getCnpj());
        logRepository.save(new Log("Fornecedor: " + fornecedor.getNome(), "DELETE", details, LocalDateTime.now()));
    }

    // Venda Logs
    public void logCreateVenda(Venda venda) {
        Produto produto = produtoRepository.findById(venda.getProdutoId()).orElse(null);
        String produtoNome = (produto != null) ? produto.getNome() : "Produto não encontrado";

        String details = String.format("Venda registrada: Produto: %s; Quantidade: %s; Data: %s Horario: %s;",
                produtoNome, venda.getQuantidade(), venda.getDataVenda().format(dateFormatter), venda.getDataVenda().format(timeFormatter));
        logRepository.save(new Log("Venda: " + produtoNome, "VENDIDO", details, LocalDateTime.now()));
    }

    public void logExportProdutos(List<Produto> produtos) {
        String details = String.format("Exportação de produtos: Total de produtos exportados: %s;", produtos.size());
        logRepository.save(new Log("Produto", "EXPORT", details, LocalDateTime.now()));
    }

    // Log de Importação de Produtos
    public void logImportProdutos(List<Produto> produtos) {
        StringBuilder details = new StringBuilder();
        details.append(String.format("Importação de produtos: Total de produtos importados: %s;", produtos.size()));
        produtos.forEach(produto -> details.append(String.format(" Produto: %s;", produto.getNome())));
        logRepository.save(new Log("Produto", "IMPORT", details.toString(), LocalDateTime.now()));
    }

    public void logExportFornecedores(List<Fornecedor> fornecedores) {
        StringBuilder details = new StringBuilder("Fornecedores exportados: ");
        for (Fornecedor fornecedor : fornecedores) {
            details.append(String.format("%s (ID: %d); ", fornecedor.getNome(), fornecedor.getId()));
        }
        logRepository.save(new Log("Fornecedores", "EXPORT", details.toString(), LocalDateTime.now()));
    }

    public void logImportFornecedores(List<Fornecedor> fornecedores) {
        StringBuilder details = new StringBuilder("Fornecedores importados: ");
        for (Fornecedor fornecedor : fornecedores) {
            details.append(String.format("%s (ID: %d); ", fornecedor.getNome(), fornecedor.getId()));
        }
        logRepository.save(new Log("Fornecedores", "IMPORT", details.toString(), LocalDateTime.now()));
    }


}
