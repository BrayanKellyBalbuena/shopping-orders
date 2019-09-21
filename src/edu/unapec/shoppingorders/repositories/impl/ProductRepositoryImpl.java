package edu.unapec.shoppingorders.repositories.impl;

import edu.unapec.shoppingorders.constants.ExcelSheetName;
import edu.unapec.shoppingorders.enums.ClientCell;
import edu.unapec.shoppingorders.enums.ProductCell;
import edu.unapec.shoppingorders.helpers.ExcelFileHelper;
import edu.unapec.shoppingorders.models.Client;
import edu.unapec.shoppingorders.models.Product;
import edu.unapec.shoppingorders.repositories.ProductRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    private ExcelFileHelper excelFileHelper;
    private Sheet productsSheet;
    private Row productRow;
    private final Integer NEW_PRODUCT = 0;
    private final int FIRST_ROW = 0;

    public ProductRepositoryImpl(String path) throws IOException {
        excelFileHelper = new ExcelFileHelper(path);
        productsSheet = excelFileHelper.getSheet(ExcelSheetName.PRODUCTS);
    }
    @Override
    public Integer add(Product entity) throws IOException {
        int newId = (int) getGeneratedIdCell().getNumericCellValue() + 1;
        Row newRow = productsSheet.createRow(newId);

        fillRow(newRow, entity, newId);
        getGeneratedIdCell().setCellValue(newId);
        excelFileHelper.Save();
        return newId;
    }

    private Cell getGeneratedIdCell() {
        return productsSheet.getRow(FIRST_ROW).getCell(ProductCell.GENERATED_ID.getIntValue());
    }

    @Override
    public void update(Product entity) throws IOException {
        productRow = findRowById(entity.getId());

        fillRow(productRow, entity, entity.getId());
        excelFileHelper.Save();
    }

    @Override
    public Product findById(Integer integer) throws IOException {
        productRow = productsSheet.getRow(integer);
        excelFileHelper.closeWorkBook();

        return getProductFromRow(productRow);
    }

    private void fillRow(Row row, Product product, Integer id) {

        if(product.getId() != NEW_PRODUCT) {
            row.getCell(ProductCell.ID.getIntValue()).setCellValue(product.getId());
            row.getCell(ProductCell.NAME.getIntValue()).setCellValue(product.getName());
            row.getCell(ProductCell.PRICE.getIntValue()).setCellValue(product.getPrice());
        }
        else {
            row.createCell(ProductCell.ID.getIntValue()).setCellValue(id);
            row.createCell(ProductCell.NAME.getIntValue()).setCellValue(product.getName());
            row.createCell(ProductCell.PRICE.getIntValue()).setCellValue(product.getPrice());
        }
    }

    @Override
    public void delete(Integer integer) throws IOException {
        productRow = findRowById(integer);
        int lastRowNum = productsSheet.getLastRowNum();
        int rowIndex = productRow.getRowNum();

        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            productsSheet.shiftRows(rowIndex + 1, lastRowNum, -1);
            productsSheet.removeRow(productsSheet.getRow(lastRowNum));
        }if(rowIndex == lastRowNum) {
            productsSheet.removeRow(productsSheet.getRow(rowIndex));
        }
        excelFileHelper.Save();
    }

    public Row findRowById(Integer id) {
        Iterator<Row> sheetIterator = productsSheet.iterator();
        int i = productsSheet.getLastRowNum();

        sheetIterator.next();

        while (sheetIterator.hasNext()) {
             productRow = sheetIterator.next();
            if(productRow.getCell(ProductCell.ID.getIntValue()).getNumericCellValue() == id) {
                return productRow;
            }
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        Iterator<Row> sheetIterator = productsSheet.iterator();
        Row productRow;
        List<Product> products = new ArrayList<>();

        sheetIterator.next();

        while (sheetIterator.hasNext()) {
            productRow = sheetIterator.next();
            if(productRow.getCell(ClientCell.ID.getIntValue()) != null) {
                products.add(getProductFromRow(productRow));
            }
        }

        return products;
    }

    private Product getProductFromRow(Row row) {
        return new Product(
                (int)(row.getCell(ProductCell.ID.getIntValue()).getNumericCellValue()),
                row.getCell(ProductCell.NAME.getIntValue()).getStringCellValue(),
                row.getCell(ProductCell.PRICE.getIntValue()).getNumericCellValue()
        );
    }
}
