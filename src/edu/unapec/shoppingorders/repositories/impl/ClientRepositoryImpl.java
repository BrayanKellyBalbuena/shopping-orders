package edu.unapec.shoppingorders.repositories.impl;

import edu.unapec.shoppingorders.enums.ClientCell;
import edu.unapec.shoppingorders.constants.ExcelSheetName;
import edu.unapec.shoppingorders.helpers.ExcelFileHelper;
import edu.unapec.shoppingorders.models.Client;
import edu.unapec.shoppingorders.repositories.ClientRepository;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {

   private ExcelFileHelper excelFileHelper;
   private Sheet clientsSheet;
   private Row clientRow;
   private Integer NEW_CLIENT = 0;

    public ClientRepositoryImpl(String path) throws IOException {
        excelFileHelper = new ExcelFileHelper(path);
        clientsSheet = excelFileHelper.getSheet(ExcelSheetName.CLIENTS);
    }
    @Override
    public Integer add(Client entity) throws IOException {
        int newId = (int) getGeneratedIdCell().getNumericCellValue() + 1;
        Row newRow = clientsSheet.createRow(newId);

        fillRow(newRow, entity, newId);
        getGeneratedIdCell().setCellValue(newId);
        excelFileHelper.Save();
        return newId;
    }

    private Cell getGeneratedIdCell() {
       return clientsSheet.getRow(0).getCell(ClientCell.GENERATED_ID.getIntValue());
    }

    @Override
    public void update(Client entity) throws IOException {
        clientRow = findRowById(entity.getId());

        fillRow(clientRow, entity, entity.getId());
        excelFileHelper.Save();
    }

    private void fillRow(Row row, Client client, Integer id) {
        if (client.getId() != NEW_CLIENT) {
            row.getCell(ClientCell.NAME.getIntValue())
                    .setCellValue(client.getName());
            row.getCell(ClientCell.LAST_NAME.getIntValue())
                    .setCellValue(client.getLastName());
            row.getCell(ClientCell.IDENTIFICATION_CARD.getIntValue())
                    .setCellValue(client.getIdentificationCard());
        } else {
            row.createCell(ClientCell.ID.getIntValue()).setCellValue(id);
            row.createCell(ClientCell.NAME.getIntValue())
                    .setCellValue(client.getName());
            row.createCell(ClientCell.LAST_NAME.getIntValue())
                    .setCellValue(client.getLastName());
            row.createCell(ClientCell.IDENTIFICATION_CARD.getIntValue())
                    .setCellValue(client.getIdentificationCard());
            row.createCell(ClientCell.CREATED_DATE.getIntValue())
                    .setCellValue(client.getCreatedDate());
        }
    }

    @Override
    public void delete(Integer integer) throws IOException {
        clientRow = findRowById(integer);
        int lastRowNum = clientsSheet.getLastRowNum();
        int rowIndex = clientRow.getRowNum();

        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            clientsSheet.shiftRows(rowIndex + 1, lastRowNum, -1);
            clientsSheet.removeRow(clientsSheet.getRow(lastRowNum));
        } if(rowIndex == lastRowNum) {
            clientsSheet.removeRow(clientsSheet.getRow(rowIndex));
        }
        excelFileHelper.Save();
    }

    @Override
    public Client findById(Integer integer) {
        clientRow = findRowById(integer);
        return getClientFromRow(clientRow);
    }

    public Row findRowById(Integer id) {
        Iterator<Row> sheetIterator = clientsSheet.iterator();
        int i = clientsSheet.getLastRowNum();
        sheetIterator.next();

        while (sheetIterator.hasNext()) {
            clientRow = sheetIterator.next();
            if(clientRow.getCell(ClientCell.ID.getIntValue()) != null) {
                if (clientRow.getCell(ClientCell.ID.getIntValue()).getNumericCellValue() == id) {
                    return clientRow;
                }
            }
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        Iterator<Row> sheetIterator = clientsSheet.iterator();
        int i = clientsSheet.getLastRowNum();
        List<Client> clients = new ArrayList<>();

       sheetIterator.next();
        while (sheetIterator.hasNext()) {
            clientRow = sheetIterator.next();
            if(clientRow.getCell(ClientCell.ID.getIntValue()) != null) {
                clients.add(getClientFromRow(clientRow));
            }
        }

        return clients;
    }

    private Client getClientFromRow(Row row) {
        Double cell = row.getCell(ClientCell.ID.getIntValue()).getNumericCellValue();
        return new Client(
                (int)(row.getCell(ClientCell.ID.getIntValue()).getNumericCellValue()),
                row.getCell(ClientCell.NAME.getIntValue()).getStringCellValue(),
                row.getCell(ClientCell.LAST_NAME.getIntValue()).getStringCellValue(),
                row.getCell(ClientCell.IDENTIFICATION_CARD.getIntValue()).getStringCellValue(),
                row.getCell(ClientCell.CREATED_DATE.getIntValue()).getStringCellValue()
        );
    }
}
