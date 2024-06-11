package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.repository.ProdutoRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    private final ProdutoRepository produtoRepository;

    public ExcelService(ProdutoRepository repository) {
        this.produtoRepository = repository;
    }

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
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}

