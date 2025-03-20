package org.example;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvDataImporter extends DataImporter {
    public CsvDataImporter(FinanceFacade facade) {
        super(facade);
    }

    @Override
    protected List<OperationDTO> parseFile(String filePath) {
        List<OperationDTO> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                OperationDTO dto = new OperationDTO();
                dto.type = OperationType.valueOf(parts[0]);
                dto.accountId = Integer.parseInt(parts[1]);
                dto.amount = Double.parseDouble(parts[2]);
                dto.date = LocalDate.parse(parts[3]);
                dto.description = parts[4];
                dto.categoryId = Integer.parseInt(parts[5]);
                result.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV", e);
        }
        return result;
    }
}
