package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Fornecedor;
import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.repository.FornecedorRepository;
import com.cadastro1.demo.repository.ProdutoRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public ByteArrayInputStream exportProdutosToExcel() throws IOException {
        String[] columns = {"ID", "Nome", "Valor", "Quantidade", "Fornecedor"};
        List<Produto> produtos = produtoRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Produtos");

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowIdx = 1;
            for (Produto produto : produtos) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(produto.getId());
                row.createCell(1).setCellValue(produto.getNome());
                row.createCell(2).setCellValue(produto.getValor());
                row.createCell(3).setCellValue(produto.getQuantidade());
                row.createCell(4).setCellValue(produto.getFornecedor());
            }

            workbook.write(out);

            // Log the export action
            logService.logExportProdutos(produtos);

            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public ByteArrayInputStream exportFornecedoresToExcel() throws IOException {
        String[] columns = {"ID", "Nome", "Telefone", "Email", "TipoProduto", "CNPJ"};
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Fornecedores");

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowIdx = 1;
            for (Fornecedor fornecedor : fornecedores) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(fornecedor.getId());
                row.createCell(1).setCellValue(fornecedor.getNome());
                row.createCell(2).setCellValue(fornecedor.getTelefone());
                row.createCell(3).setCellValue(fornecedor.getEmail());
                row.createCell(4).setCellValue(fornecedor.getTipoProduto());
                row.createCell(5).setCellValue(fornecedor.getCnpj());
            }

            workbook.write(out);

            // Log the export action
            logService.logExportFornecedores(fornecedores);

            return new ByteArrayInputStream(out.toByteArray());
        }
    }


}