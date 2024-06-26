package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Fornecedor;
import com.cadastro1.demo.model.Produto;
import com.cadastro1.demo.repository.FornecedorRepository;
import com.cadastro1.demo.repository.ProdutoRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelImportService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private FornecedorRepository fornecedorRepository;

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

            logService.logImportProdutos(produtos);
        }
    }

    public void importFornecedoresFromExcel(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Fornecedor> fornecedores = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId((long) row.getCell(0).getNumericCellValue());
                fornecedor.setNome(row.getCell(1).getStringCellValue());
                fornecedor.setTelefone(row.getCell(2).getStringCellValue());
                fornecedor.setEmail(row.getCell(3).getStringCellValue());
                fornecedor.setTipoProduto(row.getCell(4).getStringCellValue());
                fornecedor.setCnpj(row.getCell(5).getStringCellValue());

                fornecedores.add(fornecedor);
            }

            fornecedorRepository.saveAll(fornecedores);


            logService.logImportFornecedores(fornecedores);
        }
    }
}
