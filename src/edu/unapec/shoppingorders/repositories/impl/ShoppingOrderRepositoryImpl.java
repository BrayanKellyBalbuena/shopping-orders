package edu.unapec.shoppingorders.repositories.impl;

import edu.unapec.shoppingorders.enums.ClientCell;
import edu.unapec.shoppingorders.enums.ProductCell;
import edu.unapec.shoppingorders.enums.ShoppingOrderCell;
import edu.unapec.shoppingorders.constants.ExcelSheetName;
import edu.unapec.shoppingorders.helpers.ExcelFileHelper;
import edu.unapec.shoppingorders.models.Product;
import edu.unapec.shoppingorders.models.ShoppingOrder;
import edu.unapec.shoppingorders.repositories.ProductRepository;
import edu.unapec.shoppingorders.repositories.ShoppingOrderRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingOrderRepositoryImpl implements ShoppingOrderRepository {

   private ExcelFileHelper excelFileHelper;
   private Sheet           shoppingOrdersSheet;
   private Iterator<Row>   sheetIterator;
   private Row             shoppingOrderRow;
   private final Integer ORDER_NEW_ID = 0;
   private final int FIRST_ROW = 0;

    public ShoppingOrderRepositoryImpl(String path) throws IOException {
        excelFileHelper = new ExcelFileHelper(path);
        shoppingOrdersSheet = excelFileHelper.getSheet(ExcelSheetName.SHOPPING_ORDERS);
    }
    @Override
    public Integer add(ShoppingOrder entity) throws IOException {
        int newId = (int) getGeneratedIdCell().getNumericCellValue() + 1;
        Row newRow = shoppingOrdersSheet.createRow(newId);

        fillRow(newRow, entity, newId);
        getGeneratedIdCell().setCellValue(newId);
        excelFileHelper.Save();
        return newId;
    }

    private Cell getGeneratedIdCell() {
        return shoppingOrdersSheet.getRow(FIRST_ROW).getCell(ShoppingOrderCell.GENERATE_ID.getIntValue());
    }

    @Override
    public void update(ShoppingOrder entity)throws IOException {
        shoppingOrderRow = findRowById(entity.getId());

        fillRow(shoppingOrderRow, entity, entity.getId());
        excelFileHelper.Save();
    }

    private void fillRow(Row row, ShoppingOrder order, Integer id) {
        if(order.getId() != ORDER_NEW_ID) {
            row.getCell(ShoppingOrderCell.ID.getIntValue()).setCellValue(order.getId());
            row.getCell(ShoppingOrderCell.CLIENT_NAME.getIntValue()).setCellValue(order.getClientName());
            row.getCell(ShoppingOrderCell.CLIENT_ID.getIntValue()).setCellValue(order.getClientId());
            row.getCell(ShoppingOrderCell.PRODUCT_ID.getIntValue()).setCellValue(order.getProduct().getId());
            row.getCell(ShoppingOrderCell.PRODUCT_NAME.getIntValue()).setCellValue(order.getProduct().getName());
            row.getCell(ShoppingOrderCell.PRICE.getIntValue()).setCellValue(order.getProduct().getPrice());
            row.getCell(ShoppingOrderCell.QUANTITY.getIntValue()).setCellValue(order.getQuantity());
            row.getCell(ShoppingOrderCell.SUBTOTAL.getIntValue()).setCellValue(order.getSubTotal());
            row.getCell(ShoppingOrderCell.ORDER_DATE.getIntValue()).setCellValue(order.getSubTotal());
        } else {
            row.createCell(ShoppingOrderCell.ID.getIntValue()).setCellValue(id);
            row.createCell(ShoppingOrderCell.CLIENT_NAME.getIntValue()).setCellValue(order.getClientName());
            row.createCell(ShoppingOrderCell.CLIENT_ID.getIntValue()).setCellValue(order.getClientId());
            row.createCell(ShoppingOrderCell.PRODUCT_ID.getIntValue()).setCellValue(order.getProduct().getId());
            row.createCell(ShoppingOrderCell.PRODUCT_NAME.getIntValue()).setCellValue(order.getProduct().getName());
            row.createCell(ShoppingOrderCell.PRICE.getIntValue()).setCellValue(order.getProduct().getPrice());
            row.createCell(ShoppingOrderCell.QUANTITY.getIntValue()).setCellValue(order.getQuantity());
            row.createCell(ShoppingOrderCell.SUBTOTAL.getIntValue()).setCellValue(order.getSubTotal());
            row.createCell(ShoppingOrderCell.ORDER_DATE.getIntValue()).setCellValue(order.getSubTotal());
        }
    }

    @Override
    public void delete(Integer integer) throws IOException {
        shoppingOrderRow = findRowById(integer);
        int lastRowNum = shoppingOrdersSheet.getLastRowNum();
        int rowIndex = shoppingOrderRow.getRowNum();

        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            shoppingOrdersSheet.shiftRows(rowIndex + 1, lastRowNum, -1);
            shoppingOrdersSheet.removeRow(shoppingOrdersSheet.getRow(lastRowNum));
        } if(rowIndex == lastRowNum) {
            shoppingOrdersSheet.removeRow(shoppingOrdersSheet.getRow(rowIndex));
        }
        excelFileHelper.Save();
    }

    @Override
    public ShoppingOrder findById(Integer integer) {
        shoppingOrderRow =  shoppingOrdersSheet.getRow(integer);
        return getShoppingOrderFromRow(shoppingOrderRow);
    }

    public Row findRowById(Integer id) {
        Iterator<Row> sheetIterator = shoppingOrdersSheet.iterator();
        int i = shoppingOrdersSheet.getLastRowNum();
        sheetIterator.next();

        while (sheetIterator.hasNext()) {
            shoppingOrderRow = sheetIterator.next();
            if (shoppingOrderRow.getCell(ClientCell.ID.getIntValue()).getNumericCellValue() == id) {
                return shoppingOrderRow;
            }
        }
       return null;
    }

    @Override
    public List<ShoppingOrder> getAll() {
        sheetIterator = shoppingOrdersSheet.iterator();
        List<ShoppingOrder> shoppingOrders = new ArrayList<>();
        ShoppingOrder shoppingOrder;

        sheetIterator.next();

        while (sheetIterator.hasNext()) {
            if(shoppingOrderRow.getCell(ClientCell.ID.getIntValue()) != null) {
                shoppingOrderRow = sheetIterator.next();
            }
            shoppingOrders.add(getShoppingOrderFromRow(shoppingOrderRow));
        }

        return shoppingOrders;
    }

    private ShoppingOrder getShoppingOrderFromRow(Row row) {
        return new ShoppingOrder(
                (int) row.getCell(ShoppingOrderCell.ID.getIntValue() ).getNumericCellValue(),
                (int)  row.getCell(ShoppingOrderCell.CLIENT_ID.getIntValue()).getNumericCellValue(),
                row.getCell( ShoppingOrderCell.CLIENT_NAME.getIntValue() ).getStringCellValue(),

                new Product( (int) row.getCell(ShoppingOrderCell.PRODUCT_ID.getIntValue()).getNumericCellValue(),
                        row.getCell( ShoppingOrderCell.CLIENT_NAME.getIntValue() ).getStringCellValue(),
                        row.getCell( ShoppingOrderCell.PRICE.getIntValue() ).getNumericCellValue()),

                row.getCell(ShoppingOrderCell.ORDER_DATE.getIntValue()).getDateCellValue().toString(),
                row.getCell(ShoppingOrderCell.QUANTITY.getIntValue()).getNumericCellValue(),
                row.getCell( ShoppingOrderCell.SUBTOTAL.getIntValue() ).getNumericCellValue()
        );
    }

}