package org.example;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public abstract class DataImporter {
    protected final FinanceFacade facade;

    public DataImporter(FinanceFacade facade) {
        this.facade = facade;
    }

    protected abstract List<OperationDTO> parseFile(String filePath);

    public void importData(String filePath) {
        List<OperationDTO> operations = parseFile(filePath);

        for (OperationDTO dto : operations) {
            try {
                facade.addOperation(
                        dto.getType(),
                        dto.getAccountId(),
                        dto.getAmount(),
                        dto.getDate(),
                        dto.getDescription(),
                        dto.getCategoryId()
                );
                System.out.println("Imported operation: " + dto.getDescription());
            } catch (Exception e) {
                System.out.println("Error importing operation: " + dto.getDescription() + " - " + e.getMessage());
            }
        }

        System.out.println("Import completed successfully.");
    }
}
