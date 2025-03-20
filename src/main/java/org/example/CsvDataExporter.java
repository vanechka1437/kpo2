package org.example;

import org.springframework.stereotype.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CsvDataExporter implements DataExporter {
    private final FinanceFacade facade;

    public CsvDataExporter(FinanceFacade facade) {
        this.facade = facade;
    }

    @Override
    public void exportData(String filePath) {
        List<Operation> operations = facade.getOperations(); // Убедись, что метод `getOperations` есть

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Type,AccountId,Amount,Date,Description,CategoryId\n");
            for (Operation op : operations) {
                writer.write(op.getType() + "," +
                        op.getBankAccountId() + "," +
                        op.getAmount() + "," +
                        op.getDate() + "," +
                        op.getDescription() + "," +
                        op.getCategoryId() + "\n");
            }
            System.out.println("Data exported successfully to " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error exporting CSV file", e);
        }
    }
}
