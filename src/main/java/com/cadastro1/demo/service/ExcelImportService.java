package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.repository.ProdutoRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelImportService {
    private final ProdutoRepository produtoRepository;

    public ExcelImportService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void importProdutosFromExcel(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Produto> produtos = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                Produto produto = new Produto();
                produto.setId((long) row.getCell(0).getNumericCellValue());
                produto.setNome(row.getCell(1).getStringCellValue());
                produto.setValor(row.getCell(2).getNumericCellValue());
                produto.setQuantidade((int) row.getCell(3).getNumericCellValue());
                produto.setFornecedor(row.getCell(4).getStringCellValue());

                produtos.add(produto);
            }

            produtoRepository.saveAll(produtos);
        }
    }
}